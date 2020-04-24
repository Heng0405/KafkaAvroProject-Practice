import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import service.KafkaProducer;
import twitter4j.*;
import utils.KafkaUtils;

import java.io.IOException;
import java.util.Properties;

public class Application {

    public static void main(String args[]) throws TwitterException, IOException {

        KafkaProducer kafkaProducer = new KafkaProducer();
        kafkaProducer.run();



/*        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop000:9092");
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                KafkaAvroSerializer.class.getName());

        kafkaProperties.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                "http://hadoop000:8081");
        kafkaProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        final Producer kafkaProducer = new KafkaProducer(kafkaProperties);

        System.out.println(kafkaProducer.toString());*/


    }
}
