package model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Model;
import org.json.simple.JSONObject;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioBook implements Model {

    private static final String bookIdkey = "book_id";
    private String bookId;

    private static final String nameKey = "name";
    private String name;

    private static final String authorIdkey = "author_id";
    private String authorId;

    private static final String imageKey = "image";
    private String image;

    private static final String durationKey = "duration";
    private String duration;

    private static final String numberOfPartKey = "number_of_part";
    private Integer numberOfPart;

    private static final String viewsKey = "views";
    private Long views;

    private static final String accentIdKey = "accent_id";
    private String accent;

    private static final String categoryIdKey = "category_id";
    private String categoryId;

    private static final String createdAtKey = "created_at";
    private Long createdAt;

    private static final String partsKey = "parts";
    private List<Part> parts;


    public JSONObject toJsonObject() {
        JSONObject result = new JSONObject();
        if (this.bookId != null){
            result.put(bookIdkey, bookId);
        }
        if (this.authorId != null){
            result.put(authorIdkey, authorId);
        }
        if (this.image != null){
            result.put(imageKey, image);
        }
        if (this.duration != null){
            result.put(durationKey, duration);
        }
        if (this.numberOfPart != null){
            result.put(numberOfPartKey, numberOfPart);
        }
        if (this.views != null){
            result.put(viewsKey, views);
        }
        if (this.accent != null){
            result.put(accentIdKey, accent);
        }
        if (this.categoryId != null){
            result.put(categoryIdKey, categoryId);
        }
        if (this.createdAt != null){
            result.put(createdAtKey, createdAt);
        }
        if (this.parts != null){
            result.put(partsKey, parts);
        }
        if (this.name != null){
            result.put(nameKey, name);
        }
        return result;
    }
}
