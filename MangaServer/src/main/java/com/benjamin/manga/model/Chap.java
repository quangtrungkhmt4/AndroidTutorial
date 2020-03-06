package com.benjamin.manga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Chap extends AbstractModel{

    @Field("name")
    private String name;

    @Field("update_time")
    private String updateTime;

    @Field("views")
    private Long views;

    @Field("link")
    private String link;
}
