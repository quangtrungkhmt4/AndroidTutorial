package com.benjamin.manga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("category")
public class Category extends AbstractModel{

    @Field("name")
    private String name;

    @Field("count")
    private Integer count;

    @Field("link")
    private String link;

    @Field("info")
    private String info;

}
