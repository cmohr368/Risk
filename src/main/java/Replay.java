import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.Date;

public class Replay {
    private BasicAWSCredentials awsCreds;
    private AmazonS3 s3;
    String bucketName;
    String fileKey;

    public Replay() {
        awsCreds = new BasicAWSCredentials("AAAAAAAA", "AAAAAAAAAAAA");
        s3 = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        bucketName = "riskprojectreplay";
        Date currentDate = new Date();
        fileKey = currentDate.toString();
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

}
