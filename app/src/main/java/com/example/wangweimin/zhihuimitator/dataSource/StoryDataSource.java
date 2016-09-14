package com.example.wangweimin.zhihuimitator.dataSource;

import com.example.wangweimin.zhihuimitator.Model.Story;

import java.util.List;

/**
 * Created by wangweimin on 16/8/23.
 */

public interface StoryDataSource {

    interface LoadStoriesCallback {

        void onStoriesLoaded(List<Story> stories);

        void onDataNotAvailable();
    }

    void getStoriesByDate(String date, LoadStoriesCallback callback);

    void getLatestStories(LoadStoriesCallback callback);

    void getFavoriteStories(LoadStoriesCallback callback, int page);

    void getTopStories(LoadStoriesCallback callback);

    void saveStories(String date, List<Story> stories);

    void deleteStories();

    void collectStory(String id);

}
