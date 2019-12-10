package model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Model;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Model{

    private static final String authorIdkey = "author_id";
    private String authorId;

    private static final String authorNameKey = "name";
    private String authorName;


    public JSONObject toJsonObject() {
        JSONObject result = new JSONObject();
        if (this.authorId != null){
            result.put(authorIdkey, authorId);
        }
        if (this.authorName != null){
            result.put(authorNameKey, authorName);
        }
        return result;
    }
}
