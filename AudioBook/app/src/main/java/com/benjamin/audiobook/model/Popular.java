package com.benjamin.audiobook.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Popular implements Serializable {
    private String id;
    private String image;
    private String name;
    private String author;
    private int numberPart;
    private String duration;
    private long views;
}
