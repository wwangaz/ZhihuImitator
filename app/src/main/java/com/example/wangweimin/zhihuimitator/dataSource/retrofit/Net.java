package com.example.wangweimin.zhihuimitator.dataSource.retrofit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 15/10/29.
 */
public class Net {
    private static final String TAG = "HTTP";
    private static final int CONNECTION_TIME_OUT_SECOND = 15;
    private static final int READ_TIME_OUT_SECOND = 15;
    private static final String baseUrl = "http://news-at.zhihu.com/api/4/";

    public static <T> T getApi(Class<T> cls){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(cls);
    }
}
