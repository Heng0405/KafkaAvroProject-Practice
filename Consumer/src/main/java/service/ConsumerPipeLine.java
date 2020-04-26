package service;

import config.ApplicationProperties;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class ConsumerPipeLine {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConsumerPipeLine.class);

    public ConsumerPipeLine() {
    }

    public void run() throws IOException {
        logger.info("----------------------Start Stream-----------------------------");

/*        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("ConsumerPipeLine");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));*/



        //Map<String, Object> kafkaParams = new HashMap<String, Object>();
        Properties kafkaParams = ApplicationProperties.getInstance();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaParams.getProperty("kafka.bootstrap.servers"));
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaParams.getProperty("group.id"));
        kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaParams.getProperty("auto.offset.reset"));
        kafkaParams.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaParams.getProperty("kafka.schema.registry.url"));
        kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);



        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<String, GenericRecord>(kafkaParams);
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
        }






    }
}
