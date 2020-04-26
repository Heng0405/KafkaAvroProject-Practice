package utils;

import config.ApplicationProperties;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.Schema;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class KafkaUtils {
    private static final Logger logger = LoggerFactory.getLogger(KafkaUtils.class);
    public static Producer kafkaConfig() throws IOException {

        logger.info("-----------------Constructing Kafka Producer-----------------------");
        Properties kafkaProperties = ApplicationProperties.getInstance();
        //Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop000:9092");
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                KafkaAvroSerializer.class.getName());
        kafkaProperties.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                "http://hadoop000:8081");
        kafkaProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        final Producer kafkaProducer = new KafkaProducer(kafkaProperties);
        return kafkaProducer;

    }
    public Schema createSchema(){
        Schema schema = null;
        Schema.Parser parser = new Schema.Parser();
        try {
            schema = parser.parse(this.getClass().getResourceAsStream("/twitter.avsc"));
        } catch (IOException e) {
            e.printStackTrace();
        }return schema;
    }
}
