package repository.mongodb;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import constant.AccentDBKey;
import constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import model.impl.Accent;
import repository.DBManager;
import util.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AccentRepository {

    private static DBCollection collection;

    static {
        try {
            collection = DBManager.getBookDB().getCollection(AccentDBKey.COL_NAME);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    public static List<Accent> getAccents() throws ApplicationException {
        List<Accent> result = new ArrayList<Accent>();
        try {
            DBCursor cursor = collection.find();
            while (cursor.hasNext()){
                DBObject object = cursor.next();
                Accent accent = castDbObjectToModel(object);
                if (accent != null){
                    result.add(accent);
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new ApplicationException(ErrorCode.UNKNOWN_ERROR);
        }
        return result;
    }

    private static Accent castDbObjectToModel(DBObject object) {
        Accent accent = new Accent();
        accent.setAccentId((String) object.get(AccentDBKey._ID));
        accent.setAccentName((String) object.get(AccentDBKey.NAME));
        return accent;
    }

}
