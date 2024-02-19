package dev.trifanya.swing_crudapp.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public static Properties loadProperties(String resourceFileName) {
        Properties configuration = new Properties();
        try (InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName)) {
            configuration.load(inputStream);
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла конфигурации.");
            e.printStackTrace();
        }
        return configuration;
    }
}
