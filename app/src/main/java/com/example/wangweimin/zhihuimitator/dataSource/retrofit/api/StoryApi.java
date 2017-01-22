package com.example.wangweimin.zhihuimitator.dataSource.retrofit.api;

import com.example.wangweimin.zhihuimitator.model.Image;
import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.model.Themes;
import com.example.wangweimin.zhihuimitator.model.ThemesDetail;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by wangweimin on 15/9/16.
 */
public interface StoryApi {
    @GET("news/latest")
    Call<Story.StoryResult> latestStories();

    @GET("news/before/{date}")
    Call<Story.StoryResult> beforeStories(@Path("date") String date);

    @GET("news/{id}")
    Call<Story> getStoryInfo(@Path("id") String id);

    @GET("start-image/{resolution}")
    Call<Image> getStartImage(@Path("resolution") String resolution);

    @GET("themes")
    Call<Themes.ThemesResult> getThemes();

    @GET("theme/{id}")
    Call<ThemesDetail> getThemesDetail(@Path("id") String id);
}
