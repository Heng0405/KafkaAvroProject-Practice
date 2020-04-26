import service.ConsumerPipeLine;

import java.io.IOException;

public class Application {

    public static void main(String args[]) throws IOException {

        ConsumerPipeLine pipeLine = new ConsumerPipeLine();
        pipeLine.run();
    }

}
