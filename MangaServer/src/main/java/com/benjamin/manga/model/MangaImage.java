package com.benjamin.manga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("manga_image")
public class MangaImage extends AbstractModel {

    @Field("link")
    private String link;

    @Field("index")
    private Integer imageIndex;

    @Field("chap_id")
    private String chapId;
}
