package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties extends Properties {

    private static final String fileName = "producer.properties";

    private static ApplicationProperties instance = null;

    public static synchronized ApplicationProperties getInstance() throws IOException {
        if(instance != null) return instance;
        instance = new ApplicationProperties();
        InputStream inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(fileName);
        instance.load(inputStream);
        return instance;
    }



}
