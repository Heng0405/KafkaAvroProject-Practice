package utils;

import config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.util.Properties;

public class TwitterConnection {

    private static final Logger logger = LoggerFactory.getLogger(TwitterConnection.class);

    public static TwitterStream getConnection(){

       Properties twitterProperties;
       TwitterStream twitterStream = null;
        try{
            twitterProperties = ApplicationProperties.getInstance();
            logger.info("--------------Build Connection-----------------");
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setJSONStoreEnabled(true);
            cb.setDebugEnabled(true).setOAuthConsumerKey(twitterProperties.getProperty("consumerKey"))
                    .setOAuthConsumerSecret(twitterProperties.getProperty("consumerSecret"))
                    .setOAuthAccessToken(twitterProperties.getProperty("accessToken"))
                    .setOAuthAccessTokenSecret(twitterProperties.getProperty("accessTokenSecret"));
            twitterStream = new TwitterStreamFactory(cb.build())
                    .getInstance(); // First you create the Stream

        } catch (IOException e) {
            e.printStackTrace();
        }
        return twitterStream;



    }
}
