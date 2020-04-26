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



    }
}
