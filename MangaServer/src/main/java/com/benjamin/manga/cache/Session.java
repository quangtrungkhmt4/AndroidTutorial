package com.benjamin.manga.cache;

import com.benjamin.manga.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private String userId;
    private String token;

    public Session(String userId){
        this.userId = userId;
        token = UUID.randomUUID().toString();
    }

}
