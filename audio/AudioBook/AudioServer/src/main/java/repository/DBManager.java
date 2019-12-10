package repository;

import com.mongodb.DB;
import constant.DBKey;
import util.CommonDAO;

public class DBManager {
    private static final DB bookDB;

    static {
        bookDB = CommonDAO.mongo.getDB(DBKey.DB_NAME);
    }

    public static DB getBookDB() {
        return bookDB;
    }

}
