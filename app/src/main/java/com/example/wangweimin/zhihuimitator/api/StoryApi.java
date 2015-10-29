package com.example.wangweimin.zhihuimitator.api;

import com.example.wangweimin.zhihuimitator.entity.Story;
import com.example.wangweimin.zhihuimitator.entity.StoryInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by wangweimin on 15/9/16.
 */
public interface StoryApi {
    @GET("/latest")
    Callback<List<Story>> latesStories();

    @GET("/before/{date}")
    Callback<List<Story>> beforeStories(@Path("date") int date);

    @GET("/{id}")
    Callback<StoryInfo> getStoryInfo(@Path("id") int id);
}
