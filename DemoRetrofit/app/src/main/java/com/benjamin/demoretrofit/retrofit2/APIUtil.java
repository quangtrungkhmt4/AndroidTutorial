package com.benjamin.demoretrofit.retrofit2;

public class APIUtil {

    public static final String BASE_URL = "http://3.135.247.235/";

    public static DataClient getData(){
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}
