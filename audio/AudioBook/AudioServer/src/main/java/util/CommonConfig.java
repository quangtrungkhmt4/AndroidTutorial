package util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonConfig {
    private static final String SERVICE_PORT_CONFIG_KEY = "SERVICE_PORT";
    public static int SERVICE_PORT = 0;

    private static final String DB_SERVER_CONFIG_KEY = "DB_SERVER";
    public static String DB_SERVER = "localhost";

    private static final String DB_PORT_CONFIG_KEY = "DB_PORT";
    public static int DB_PORT = 27017;

    private static final String LOG_FILE_CONFIG_KEY = "LOG_FILE";
    public static String LOG_FILE = "logger.log";

    private static final String LOG_PATTERN_CONFIG_KEY = "LOG_PATTERN";
    public static String LOG_PATTERN = "[%p] %m%n";

    private static final String LOG_LEVEL_CONFIG_KEY = "LOG_LEVEL";
    public static String LOG_LEVEL = "ERROR";

    private static final String DB_CONNECTION_PER_HOST_CONFIG_KEY = "DB_CONNECTION_PER_HOST";
    public static int DB_CONNECTION_PER_HOST = 1500;

    private static final String LOG_API_KEY = "LOG_API";
    public static boolean LOG_API = false;

    private static final String LOG_API_TIME_KEY = "LOG_API_TIME";
    public static String LOG_API_TIME = "2100_0259";

    private static final String REDIS_PASSWORD_CONFIG_KEY = "REDIS_PASSWORD";
    public static String REDIS_PASSWORD = "123456123";

    private static final String DB_USER_CONFIG_KEY = "DB_USER";
    public static String DB_USER = "Nexiv";

    private static final String DB_PASSWORD_CONFIG_KEY = "DB_PASSWORD";
    public static String DB_PASSWORD = "100manUser";

    private static final String DB_AUTHEN_DB_CONFIG_KEY = "DB_AUTHEN_DB";
    public static String DB_AUTHEN_DB = "admin";


    public static void initConfig(String configFile) {
        try {

            SERVICE_PORT =  ConfigMethod.configIntProperty(configFile, SERVICE_PORT_CONFIG_KEY, "0");

            DB_SERVER = ConfigMethod.configStringProperty(configFile, DB_SERVER_CONFIG_KEY, "localhost");

            DB_PORT =  ConfigMethod.configIntProperty(configFile, DB_PORT_CONFIG_KEY, "27017");

            LOG_LEVEL = ConfigMethod.configStringProperty(configFile, LOG_LEVEL_CONFIG_KEY, "ERROR");

            LOG_FILE = ConfigMethod.configStringProperty(configFile, LOG_FILE_CONFIG_KEY, "logger.log");

            LOG_PATTERN = ConfigMethod.configStringProperty(configFile, LOG_PATTERN_CONFIG_KEY, "[%p] %m%n");

            DB_CONNECTION_PER_HOST = ConfigMethod.configIntProperty(configFile, DB_CONNECTION_PER_HOST_CONFIG_KEY, "1500");

            LOG_API = ConfigMethod.configBooleanProperty(configFile, LOG_API_KEY);

            LOG_API_TIME = ConfigMethod.configStringProperty(configFile, LOG_API_TIME_KEY, "0000_2359");

            REDIS_PASSWORD = ConfigMethod.configStringProperty(configFile, REDIS_PASSWORD_CONFIG_KEY, "123456123");

            DB_USER = ConfigMethod.configStringProperty(configFile, DB_USER_CONFIG_KEY, "dbadmin");

            DB_PASSWORD = ConfigMethod.configStringProperty(configFile, DB_PASSWORD_CONFIG_KEY, "adminntq");

            DB_AUTHEN_DB = ConfigMethod.configStringProperty(configFile, DB_AUTHEN_DB_CONFIG_KEY, "admin");

        } catch (Exception ex) {
            log.error(ex.getMessage());

        }
    }

}
