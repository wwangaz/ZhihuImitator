package com.example.wangweimin.zhihuimitator.dataSource.retrofit.api;

import com.example.wangweimin.zhihuimitator.Model.Story;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by wangweimin on 15/9/16.
 */
public interface StoryApi {
    @GET("latest")
    Call<Story.StoryResult> latestStories();

    @GET("before/{date}")
    Call<Story.StoryResult> beforeStories(@Path("date") String date);

    @GET("{id}")
    Call<Story> getStoryInfo(@Path("id") String id);
}
