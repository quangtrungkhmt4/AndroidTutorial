package com.benjamin.adachimitsurumanga.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chap implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("regTime")
    @Expose
    private Long regTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("updateTime")
    @Expose
    private String updateTime;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("link")
    @Expose
    private String link;

}
