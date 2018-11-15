import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class TwitterController {

    Properties properties = new Properties();
    InputStream input = null;
    HashMap<String,String> authroization = new HashMap<>();
    TwitterController TwitterController;
    User user;
    Twitter twitter;

    TwitterController()
    {
        try {
            input = new FileInputStream("src/main/secret_FruitCakes.properties");
            properties.load(input);

            authroization.put("Access Token",properties.getProperty("twitter.accessToken"));
            authroization.put("Access Token Secret",properties.getProperty("twitter.accessTokenSecret"));

            authroization.put("Consumer Key",properties.getProperty("twitter.consumerKey"));
            authroization.put("Consumer Secret",properties.getProperty("twitter.consumerSecret"));

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

        twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(authroization.get("Consumer Key"),authroization.get("Consumer Secret"));
        AccessToken tokenAccess = new AccessToken(authroization.get("Access Token"), authroization.get("Access Token Secret"));
        twitter.setOAuthAccessToken(tokenAccess);

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
