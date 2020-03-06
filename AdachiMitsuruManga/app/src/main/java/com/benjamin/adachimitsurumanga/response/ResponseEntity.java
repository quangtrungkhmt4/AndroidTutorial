package com.benjamin.adachimitsurumanga.response;

import com.benjamin.adachimitsurumanga.model.Manga;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity implements Serializable {

    private int code;
    private List<Manga> data;
}
