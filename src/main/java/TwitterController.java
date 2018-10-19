import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class TwitterController {

    Properties properties = new Properties();
    InputStream input = null;
    HashMap<String,String> credentials = new HashMap<>();
    TwitterController TwitterController;
    User user;
    Twitter twitter;



    public HashMap<String,String> getKeys()
    {
        try {
            input = new FileInputStream("secret_FruitCakes.properties");
            properties.load(input);

            credentials.put("Consumer Key",properties.getProperty("twitter.consumerKey"));
            credentials.put("Consumer Secret",properties.getProperty("twitter.consumerSecret"));
            credentials.put("Access Token",properties.getProperty("twitter.accessToken"));
            credentials.put("Access Token Secret",properties.getProperty("twitter.accessTokenSecret"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return credentials;
    }
    public void connectTwitter(HashMap<String,String> cre)
    {
        twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(credentials.get("Consumer Key"),credentials.get("Consumer Secret"));
        AccessToken accesstoken = new AccessToken(credentials.get("Access Token"), credentials.get("Access Token Secret"));
        twitter.setOAuthAccessToken(accesstoken);
        try {
            user = twitter.verifyCredentials();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }
    public void postTweet(String str)
    {
        try {
            Status status = twitter.updateStatus(str);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

}
