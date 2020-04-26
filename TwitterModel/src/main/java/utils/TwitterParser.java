package utils;

import model.TweetObject;
import twitter4j.Status;

public class TwitterParser {

    public static TweetObject parseStatus(Status status){
        TweetObject tweetObject = new TweetObject();
        tweetObject.setStatusId(status.getId());
        tweetObject.setDisplayName(status.getUser().getScreenName());
        tweetObject.setDate(status.getCreatedAt().getTime());
        tweetObject.setRetweetCount(status.getRetweetCount());
        tweetObject.setTweetText(status.getText());
        return tweetObject;

    }
}
