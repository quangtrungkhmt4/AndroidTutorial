package com.benjamin.manga.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class User extends AbstractModel{

    @Field("email")
    private String email;

    @Field("user_name")
    private String userName;

    @Field("password")
    private String hashPass;

    @Field("is_verify")
    private Boolean isVerify;
}
