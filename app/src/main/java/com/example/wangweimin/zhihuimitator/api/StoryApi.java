package com.example.wangweimin.zhihuimitator.api;

import com.example.wangweimin.zhihuimitator.entity.Story;
import com.example.wangweimin.zhihuimitator.entity.StoryInfo;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by wangweimin on 15/9/16.
 */
public interface StoryApi {
    @GET("latest")
    Call<Story.StoryResult> latestStories();

    @GET("before/{date}")
    Call<Story.StoryResult> beforeStories(@Path("date") int date);

    @GET("{id}")
    Call<StoryInfo> getStoryInfo(@Path("id") int id);
}
