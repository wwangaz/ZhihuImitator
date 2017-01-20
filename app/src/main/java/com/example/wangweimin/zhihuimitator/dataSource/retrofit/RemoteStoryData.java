package com.example.wangweimin.zhihuimitator.dataSource.retrofit;

import com.example.wangweimin.zhihuimitator.dataSource.StoryDataSource;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.api.StoryApi;
import com.example.wangweimin.zhihuimitator.model.Story;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 16/8/24.
 */

public class RemoteStoryData implements StoryDataSource {

    private final static String SPLASH_URL = "http://news-at.zhihu.com/api/4/start-image/";

    private static RemoteStoryData Instance;

    public static RemoteStoryData getInstance() {
        if (Instance == null)
            Instance = new RemoteStoryData();
        return Instance;
    }

    @Override
    public void getStoriesByDate(String date, final LoadStoriesCallback callback) {
        Call<Story.StoryResult> call = Net.getApi(StoryApi.class).beforeStories(date);
        call.enqueue(new Callback<Story.StoryResult>() {
            @Override
            public void onResponse(Response<Story.StoryResult> response, Retrofit retrofit) {
                if (response.body() != null)
                    callback.onStoriesLoaded(response.body().stories);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getLatestStories(final LoadStoriesCallback callback) {
        Call<Story.StoryResult> call = Net.getApi(StoryApi.class).latestStories();
        call.enqueue(new Callback<Story.StoryResult>() {
            @Override
            public void onResponse(Response<Story.StoryResult> response, Retrofit retrofit) {
                if (response.body() != null)
                    callback.onStoriesLoaded(response.body().stories);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getTopStories(final LoadStoriesCallback callback) {
        Call<Story.StoryResult> call = Net.getApi(StoryApi.class).latestStories();
        call.enqueue(new Callback<Story.StoryResult>() {
            @Override
            public void onResponse(Response<Story.StoryResult> response, Retrofit retrofit) {
                if (response.body() != null)
                    callback.onStoriesLoaded(response.body().top_stories);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getFavoriteStories(LoadStoriesCallback callback, int page) {
        // TODO: 16/8/24 need to login
    }

    @Override
    public void saveStories(String date, List<Story> stories) {

    }

    @Override
    public void deleteStories() {

    }

    @Override
    public void collectStory(String id) {
        // TODO: 16/8/24 need to login
    }

}
