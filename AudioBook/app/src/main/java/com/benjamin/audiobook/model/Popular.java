package com.benjamin.audiobook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Popular {
    private String id;
    private String image;
    private String name;
    private String author;
    private int numberPart;
    private String duration;
    private long views;
}
