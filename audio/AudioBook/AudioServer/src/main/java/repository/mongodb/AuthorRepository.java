package repository.mongodb;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import constant.AuthorDBKey;
import constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import model.impl.Author;
import repository.DBManager;
import util.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthorRepository {

    private static DBCollection collection;

    static {
        try {
            collection = DBManager.getBookDB().getCollection(AuthorDBKey.COL_NAME);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    public static List<Author> getAuthors() throws ApplicationException {
        List<Author> result = new ArrayList<Author>();
        try {
            DBCursor cursor = collection.find();
            while (cursor.hasNext()){
                DBObject object = cursor.next();
                Author author = castDbObjectToModel(object);
                if (author != null){
                    result.add(author);
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new ApplicationException(ErrorCode.UNKNOWN_ERROR);
        }
        return result;
    }

    private static Author castDbObjectToModel(DBObject object) {
        Author author = new Author();
        author.setAuthorId((String) object.get(AuthorDBKey._ID));
        author.setAuthorName((String) object.get(AuthorDBKey.NAME));
        return author;
    }
}
