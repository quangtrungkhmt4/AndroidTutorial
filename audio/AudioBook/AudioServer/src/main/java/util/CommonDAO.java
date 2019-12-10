package util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CommonDAO {
    public static MongoClient mongo;

    public static void init() {
        try {
            ServerAddress sa = new ServerAddress(CommonConfig.DB_SERVER);
            MongoCredential credential = MongoCredential.createCredential(
                    CommonConfig.DB_USER,
                    CommonConfig.DB_AUTHEN_DB,
                    CommonConfig.DB_PASSWORD.toCharArray());
            MongoClientOptions mongoOptions
                    = new MongoClientOptions.Builder()
                    .connectionsPerHost(CommonConfig.DB_CONNECTION_PER_HOST)
                    .build();
            mongo = new MongoClient(sa, Arrays.asList(credential), mongoOptions);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    public static MongoClient getConnection() {
        return mongo;
    }
}
