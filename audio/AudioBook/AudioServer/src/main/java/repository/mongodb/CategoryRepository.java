package repository.mongodb;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import constant.CategoryDBKey;
import constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import model.impl.Category;
import repository.DBManager;
import util.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CategoryRepository{
    private static DBCollection collection;

    static {
        try {
            collection = DBManager.getBookDB().getCollection(CategoryDBKey.COL_NAME);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    public static List<Category> getCategories() throws ApplicationException {
        List<Category> result = new ArrayList<Category>();
        try {
            DBCursor cursor = collection.find();
            while (cursor.hasNext()){
                DBObject object = cursor.next();
                Category category = castDbObjectToModel(object);
                if (category != null){
                    result.add(category);
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new ApplicationException(ErrorCode.UNKNOWN_ERROR);
        }
        return result;
    }

    public static Category castDbObjectToModel(DBObject object) {
        Category category = new Category();
        category.setCategoryId((String) object.get(CategoryDBKey._ID));
        category.setCategoryName((String) object.get(CategoryDBKey.NAME));
        return category;
    }
}
