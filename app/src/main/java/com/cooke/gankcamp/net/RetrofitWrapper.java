package com.cooke.gankcamp.net;

import com.cooke.gankcamp.contsants.GankUrl;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by kch on 2017/11/28.
 */

public class RetrofitWrapper {

    private Retrofit mRetrofit;

    private static final int READ_TIMEOUT=60;
    private static final int CONN_TIMEOUT=30;

    public Retrofit creatRetrofit(){
        if (mRetrofit==null){
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            mRetrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(GankUrl.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())//返回值为Gson的支持(以实体类返回)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//返回值为Oservable<T>的支持
            .build();
        }
        return  mRetrofit;
    }

    public <T> T createService(Class<T> service){
       return creatRetrofit().create(service);
    }
}
