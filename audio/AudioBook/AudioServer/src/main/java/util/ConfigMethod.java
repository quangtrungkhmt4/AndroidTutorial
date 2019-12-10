package util;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class ConfigMethod {
    public static int configIntProperty(String fileName, String propertyName, String defaulValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName, defaulValue);
        int result = 0;
        try {
            result = Integer.parseInt(property.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return result;
    }

    public static double configDoubleProperty(String fileName, String propertyName, String defaulValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName, defaulValue);
        double result = 0;
        try {
            result = Double.parseDouble(property.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return result;
    }


    public static String configStringProperty(String fileName, String propertyName, String defaulValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        return serverProperties.getProperty(propertyName, defaulValue).trim();
    }

    public static Boolean configBooleanProperty(String fileName, String propertyName) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName);
        return property != null && property.trim().equalsIgnoreCase("true");
    }

    public static Boolean configBooleanProperty(String fileName, String propertyName, boolean defaultValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName);
        return property == null? defaultValue : property.trim().equalsIgnoreCase("true");
    }
}
