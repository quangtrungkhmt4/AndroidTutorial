package model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Model;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Part implements Model {

    private static final String nameKey = "name";
    private String name;

    private static final String urlKey = "url";
    private String url;


    public JSONObject toJsonObject() {
        JSONObject result = new JSONObject();
        if (this.name != null){
            result.put(nameKey, name);
        }
        if (this.url != null){
            result.put(urlKey, url);
        }
        return result;
    }
}
