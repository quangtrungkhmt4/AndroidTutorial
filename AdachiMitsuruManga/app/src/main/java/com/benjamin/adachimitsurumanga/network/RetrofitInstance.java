package com.benjamin.adachimitsurumanga.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.2.39:8080";

    public static Retrofit getInstance(){
//        OkHttpClient builder = new OkHttpClient.Builder()
//                .readTimeout(5000, TimeUnit.MICROSECONDS)
//                .writeTimeout(5000, TimeUnit.MICROSECONDS)
//                .connectTimeout(10000, TimeUnit.MICROSECONDS)
//                .retryOnConnectionFailure(true).build();
        if (retrofit == null){
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(builder)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }
}
