import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class Replay {
    private BasicAWSCredentials awsCreds;
    private AmazonS3 s3;
    String bucketName;
    String fileKey;
    Properties properties = new Properties();
    InputStream input = null;

    public Replay() {
        try {
            input = new FileInputStream("src/main/java/secret_FruitCakes.properties");
            properties.load(input);
            String accessKey = properties.getProperty("AWS.accessKey");
            String secretKey = properties.getProperty("AWS.secretKey");
            awsCreds = new BasicAWSCredentials(accessKey, secretKey);
            s3 = AmazonS3ClientBuilder.standard()
                    .withRegion("us-east-2")
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();
            bucketName = "riskprojectreplay";
            Date currentDate = new Date();
            fileKey = currentDate.toString();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public String getBucketName(){
        return bucketName;
    }
    public String getFileKey(){
        return fileKey;
    }
    public void uploadFile(File f){
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, fileKey, f);
            s3.putObject(request);
        } catch (
                AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
    public void shutdown(){
        s3.shutdown();
    }

}
