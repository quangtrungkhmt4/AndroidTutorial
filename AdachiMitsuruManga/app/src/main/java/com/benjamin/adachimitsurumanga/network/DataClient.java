package com.benjamin.adachimitsurumanga.network;

import com.benjamin.adachimitsurumanga.model.Manga;
import com.benjamin.adachimitsurumanga.response.ResponseEntity;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataClient {

    @GET("/mangas")
    Call<ResponseEntity> getMangas(@Query("page")int page, @Query("take")int take);
}
