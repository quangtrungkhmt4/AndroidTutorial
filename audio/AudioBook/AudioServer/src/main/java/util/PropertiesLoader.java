package util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertiesLoader {
    private PropertiesLoader() {
    }

    public static Properties load(String propertyFileName) {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(propertyFileName);
            properties.load(fis);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return properties;
    }
}
