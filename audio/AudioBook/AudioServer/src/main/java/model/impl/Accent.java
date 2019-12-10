package model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Model;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accent implements Model {

    private static final String accentIdkey = "accent_id";
    private String accentId;

    private static final String accentNameKey = "name";
    private String accentName;


    public JSONObject toJsonObject() {
        JSONObject result = new JSONObject();
        if (this.accentId != null){
            result.put(accentIdkey, accentId);
        }
        if (this.accentName != null){
            result.put(accentNameKey, accentName);
        }
        return result;
    }
}
