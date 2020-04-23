package service;

import model.TweetObject;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.LoggerFactory;
import twitter4j.*;
import utils.KafkaUtils;
import utils.TwitterConnection;
import utils.TwitterParser;

import java.io.IOException;

public class KafkaProducer {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(KafkaProducer.class);


    public void run() throws TwitterException, IOException {
        final KafkaUtils kafkaUtils = new KafkaUtils();
        final Schema schema = kafkaUtils.createSchema();
        final Producer producer = KafkaUtils.kafkaConfig();

        TwitterStream twitterStream = TwitterConnection.getConnection();
        StatusListener listener = new StatusListener() {
            public void onException(Exception e) {

            }

            public void onStatus(Status status) {
                TweetObject tweetObject = TwitterParser.parseStatus(status);
                GenericRecord genericRecord = new GenericData.Record(schema);
                genericRecord.put("statusId", tweetObject.statusId);
                genericRecord.put("displayName", tweetObject.displayName);
                genericRecord.put("date", tweetObject.date);
                genericRecord.put("retweetCount", tweetObject.retweetCount);
                genericRecord.put("tweetText", tweetObject.tweetText);
                ProducerRecord<String, Object> record = new ProducerRecord<String, Object>("TwitterAvro", genericRecord);


                try {
                    producer.send(record);
                    System.out.println("test---------------"+record);
                } catch (SerializationException e) {
                    e.printStackTrace();
                }


            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            public void onTrackLimitationNotice(int i) {

            }

            public void onScrubGeo(long l, long l1) {

            }

            public void onStallWarning(StallWarning stallWarning) {

            }
        };

        twitterStream.addListener(listener);
        twitterStream.sample();
        logger.info("Starting the twitter stream.");
    };
}
