package com.nexuslink.cyclenavi.Util;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Rye on 2017/1/21.
 */

public  class RetrofitWrapper {
    private static RetrofitWrapper instance;
    private Retrofit retrofit;

    private static final String BASE_URL = "http://120.77.87.78:8080";

    private RetrofitWrapper() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static RetrofitWrapper getInstance(){
        if(instance == null){
             instance = new RetrofitWrapper();
        }
        return instance;
    }
    public <T> T  create(Class<T> service){
        return retrofit.create(service);
    }

}
