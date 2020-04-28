package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.ApplicationProperties;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import model.TweetObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.serializer.KryoSerializer;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class ConsumerPipeLine implements Serializable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConsumerPipeLine.class);
    ObjectMapper objectMapper = new ObjectMapper();




    public ConsumerPipeLine() {
    }



    public void run() throws IOException {
        logger.info("----------------------Start Stream-----------------------------");

        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("ConsumerPipeLine");
        conf.set("spark.serializer", KryoSerializer.class.getName());
        JavaStreamingContext streamingContext = new JavaStreamingContext(conf, Durations.seconds(1));


        Map<String, Object> kafkaParams = new HashMap<String, Object>();
        Properties properties = ApplicationProperties.getInstance();
        kafkaParams.put("bootstrap.servers", properties.getProperty("kafka.bootstrap.servers"));
        kafkaParams.put("key.deserializer", StringDeserializer.class.getName());
        kafkaParams.put("value.deserializer",KafkaAvroDeserializer.class.getName());
        kafkaParams.put("group.id", properties.getProperty("group.id"));
        kafkaParams.put("auto.offset.reset", properties.getProperty("auto.offset.reset"));
        kafkaParams.put("schema.registry.url",properties.getProperty("kafka.schema.registry.url"));
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Arrays.asList("TwitterAvro");


        JavaInputDStream<ConsumerRecord<String, Object>> stream =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String,Object>Subscribe(topics,kafkaParams)
                );
        JavaDStream<String> lines = stream.map(stringStringConsumerRecord -> stringStringConsumerRecord.value().toString());

        List<TweetObject> tweetObjects = new ArrayList<>();


        lines.foreachRDD(rdd ->{
            JavaRDD<TweetObject> rddTwitter = rdd.map(mappedRdd -> objectMapper.readValue(mappedRdd.getBytes(),TweetObject.class)).cache();
        /*    JavaPairRDD<Integer,String> retweets = rddTwitter.mapToPair(retwittesCount -> new Tuple2<>(retwittesCount.getRetweetCount(),retwittesCount.tweetText));
            retweets.sortByKey(false).take(10);  //the most retwitted twittes.
            retweets.foreach(x-> System.out.println("test---------------------------"+x));*/

            rddTwitter.foreach(x -> System.out.println("test--------"+x.displayName+"------------"+x.statusId+"---------------"+x.getTweetText()));




        });

        // Start the computation
        streamingContext.start();
        try {
            streamingContext.awaitTermination();
            logger.info("Stop the job");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

/*        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<String, GenericRecord>(kafkaParams);
        consumer.subscribe(Arrays.asList("TwitterAvro"));
        try {
            while (true) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(1000);
                for (ConsumerRecord<String, GenericRecord> record : records) {
                    GenericRecord twitter = record.value();
                    System.out.println("value = [twitter.statusId = " + twitter.get("statusId") + ", " + "twitter.ndisplayName = "
                            + twitter.get("displayName") + ", " + "twitter.date = " + twitter.get("date")  + ", " + "twitter.tweetText = "+ twitter.get("tweetText")+"], "
                            + "partition = " + record.partition() + ", " + "offset = " + record.offset());
                }
            }
        } finally {
            consumer.close();
        }*/






    }
}
