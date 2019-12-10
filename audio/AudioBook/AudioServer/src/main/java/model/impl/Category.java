package model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Model;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Model {

    private static final String categoryIdkey = "category_id";
    private String categoryId;

    private static final String categoryNameKey = "name";
    private String categoryName;

    public JSONObject toJsonObject() {
        JSONObject result = new JSONObject();
        if (this.categoryId != null){
            result.put(categoryIdkey, categoryId);
        }
        if (this.categoryName != null){
            result.put(categoryNameKey, categoryName);
        }
        return result;
    }
}
