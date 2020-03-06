package com.benjamin.adachimitsurumanga.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manga implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("regTime")
    @Expose
    private Long regTime;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("updateTime")
    @Expose
    private String updateTime;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("ranking")
    @Expose
    private Double ranking;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("chaps")
    @Expose
    private List<Chap> chaps = null;
    @SerializedName("link")
    @Expose
    private String link;
}
