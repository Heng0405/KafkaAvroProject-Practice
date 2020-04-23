import service.KafkaProducer;
import twitter4j.*;
import utils.KafkaUtils;

import java.io.IOException;

public class Application {

    public static void main(String args[]) throws TwitterException, IOException {
        KafkaProducer producer = new KafkaProducer();
        producer.run();



    }
}
