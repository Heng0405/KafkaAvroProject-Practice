package model;

import java.io.Serializable;
import java.util.Date;

public class TweetObject implements Serializable {

    public TweetObject(){};

    public Long statusId;

    public String displayName;

    public Long date;

    public int retweetCount;

    public String tweetText;

    public Long getStatusId() {
        return statusId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Long getDate() {
        return date;
    }

    public int getRetweetCount() {
        return retweetCount;
    }


    public String getTweetText() {
        return tweetText;
    }

    @Override
    public String toString() {
        return "TweetObject{" +
                "statusId=" + statusId +
                ", displayName='" + displayName + '\'' +
                ", date=" + date +
                ", retweetCount=" + retweetCount +
                ", tweetText='" + tweetText + '\'' +
                '}';
    }

    public TweetObject(Long statusId, String displayName, Long date, int retweetCount, int favoriteCount, String country, String countryCode, String source, String tweetText) {
        this.statusId = statusId;
        this.displayName = displayName;
        this.date = date;
        this.retweetCount = retweetCount;
        this.tweetText = tweetText;
    }


    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }
}
