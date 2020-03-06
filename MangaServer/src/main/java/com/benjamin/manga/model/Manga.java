package com.benjamin.manga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("manga")
public class Manga extends AbstractModel{

    @Field("author")
    private String author;

    @Field("status")
    private String status;

    @Field("category")
    private String category;

    @Field("name")
    private String name;

    @Field("image_url")
    private String imageUrl;

    @Field("update_time")
    private String updateTime;

    @Field("views")
    private Long views;

    @Field("ranking")
    private Double ranking;

    @Field("desc")
    private String desc;

    @Field("chaps")
    private List<Chap> chaps;

    @Field("link")
    private String link;

    @Override
    public String toString() {
        return "Manga{" +
                "author='" + author + '\'' +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", views=" + views +
                ", ranking=" + ranking +
                ", desc='" + desc + '\'' +
                ", chaps=" + chaps +
                ", link='" + link + '\'' +
                '}';
    }
}
