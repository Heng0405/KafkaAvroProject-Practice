package service;

import config.ApplicationProperties;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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


    public KafkaConsumer run() throws IOException {
        logger.info("Start Stream");

        //SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("ConsumerPipeLine");
        //JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));



        //Map<String, Object> kafkaParams = new HashMap<String, Object>();
        Properties kafkaParams = ApplicationProperties.getInstance();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaParams.getProperty("kafka.bootstrap.servers"));
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaParams.getProperty("group.id"));
        kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaParams.getProperty("auto.offset.reset"));
        kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        Collection<String> topics = Arrays.asList("TwitterAvro");
        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<String, GenericRecord>(kafkaParams);
        System.out.println(consumer.toString());
        return consumer;







    }
}
