package com.nexuslink.cyclenavi.Extra.Net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit客户端
 * Created by Rye on 2017/5/5.
 */

public class RetrofitClient {
    private static final long OK_HTTP_TIME_OUT = 10;// 10s超时
    private static final String BASE_URL = "http://120.77.87.78:8080";
    private static Retrofit instance;

    public static Retrofit getInstance(){
        if(instance == null){
            OkHttpClient okhttpclient = new OkHttpClient.Builder()
                    .connectTimeout(OK_HTTP_TIME_OUT, TimeUnit.SECONDS).build();

            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okhttpclient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return instance;
        }else return instance;
    }

    public static <T> T create(Class<T> service) {
        return getInstance().create(service);
    }
}
