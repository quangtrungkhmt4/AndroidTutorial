package repository.mongodb;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import constant.BookDBKey;
import constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import model.impl.AudioBook;
import org.json.simple.JSONArray;
import repository.DBManager;
import util.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BookRepository {

    private static DBCollection collection;

    static {
        try {
            collection = DBManager.getBookDB().getCollection(BookDBKey.COL_NAME);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    public static List<AudioBook> getBooks(int limit, int offset) throws ApplicationException {
        List<AudioBook> result = new ArrayList<AudioBook>();
        try {
            DBCursor cursor = collection.find().limit(limit).skip(offset);
            while (cursor.hasNext()){
                DBObject object = cursor.next();
                AudioBook book = castDbObjectToModel(object);
                if (book != null){
                    result.add(book);
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new ApplicationException(ErrorCode.UNKNOWN_ERROR);
        }
        return result;
    }

    public static AudioBook castDbObjectToModel(DBObject object) {
        AudioBook audioBook = new AudioBook();
        audioBook.setBookId((String) object.get(BookDBKey._ID));
        audioBook.setName((String) object.get(BookDBKey.NAME));
        audioBook.setAccent((String) object.get(BookDBKey.ACCENT));
        audioBook.setAuthorId((String) object.get(BookDBKey.AUTHOR));
        audioBook.setCategoryId((String) object.get(BookDBKey.CATEGORY));
        audioBook.setCreatedAt((Long) object.get(BookDBKey.CREATED_AT));
        audioBook.setDuration((String) object.get(BookDBKey.DURATION));
        audioBook.setImage((String) object.get(BookDBKey.IMAGE));
        audioBook.setNumberOfPart((Integer) object.get(BookDBKey.NUMBER_PART));
        audioBook.setViews((Long) object.get(BookDBKey.VIEWS));

        JSONArray partArray = (JSONArray) object.get(BookDBKey.PART);
        for (Object obj : partArray){

        }

        return audioBook;
    }
}
