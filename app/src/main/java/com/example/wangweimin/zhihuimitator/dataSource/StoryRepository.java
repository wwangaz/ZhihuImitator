package com.example.wangweimin.zhihuimitator.dataSource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.dataSource.db.StoriesDB;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.RemoteStoryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by wangweimin on 16/8/23.
 */

public class StoryRepository implements StoryDataSource {

    private static StoryRepository INSTANCE;

    private final StoryDataSource mLocalDataSource;

    private final StoryDataSource mRemoteDataSource;

    private HashMap<String, List<Story>> mCachedStories;

    private boolean mCachedIsDirty = false;

    public static StoryRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new StoryRepository(StoriesDB.getInstance(context), RemoteStoryData.getInstance());
        }

        return INSTANCE;
    }

    private StoryRepository(@NonNull StoryDataSource localDataSource,
                            @NonNull StoryDataSource remoteDataSource) {

        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getStoriesByDate(final String date, final LoadStoriesCallback callback) {
        if (mCachedStories != null && !mCachedIsDirty) {
            if (mCachedStories.get(date) != null) {
                callback.onStoriesLoaded(mCachedStories.get(date));
                return;
            }
        }

        if (mCachedIsDirty) {
            getStoriesFromRemoteDataSource(date, callback);
        } else {
            mLocalDataSource.getStoriesByDate(date, new LoadStoriesCallback() {
                @Override
                public void onStoriesLoaded(List<Story> stories) {
                    refreshCache(date, stories);
                    callback.onStoriesLoaded(mCachedStories.get(date));
                }

                @Override
                public void onDataNotAvailable() {
                    getStoriesFromRemoteDataSource(date, callback);
                }
            });
        }
    }

    @Override
    public void getLatestStories(final LoadStoriesCallback callback) {
        mRemoteDataSource.getLatestStories(new LoadStoriesCallback() {
            @Override
            public void onStoriesLoaded(List<Story> stories) {
                callback.onStoriesLoaded(stories);
            }

            @Override
            public void onDataNotAvailable() {
                // TODO: 16/8/24 完善这一部分缓存
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getFavoriteStories(LoadStoriesCallback callback, int page) {
        mLocalDataSource.getFavoriteStories(callback, page);
    }

    @Override
    public void saveStories(String date, List<Story> stories) {
        mLocalDataSource.saveStories(date, stories);

        if (mCachedStories == null)
            mCachedStories = new LinkedHashMap<>();

        if (!mCachedStories.containsKey(date))
            mCachedStories.put(date, new ArrayList<Story>());

        List<Story> list = mCachedStories.get(date);
        list.addAll(stories);
        mCachedStories.put(date, list);
    }

    @Override
    public void deleteStories() {
        mLocalDataSource.deleteStories();
        mRemoteDataSource.deleteStories();

        if (mCachedStories == null)
            mCachedStories = new LinkedHashMap<>();

        mCachedStories.clear();
    }

    @Override
    public void collectStory(String id) {
        mLocalDataSource.collectStory(id);
    }

    private void getStoriesFromRemoteDataSource(final String date, final LoadStoriesCallback callback) {
        mRemoteDataSource.getStoriesByDate(date, new LoadStoriesCallback() {
            @Override
            public void onStoriesLoaded(List<Story> stories) {
                refreshCache(date, stories);
                refreshLocalDataSource(date, stories);
                callback.onStoriesLoaded(mCachedStories.get(date));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getTopStories(LoadStoriesCallback callback) {
        mRemoteDataSource.getTopStories(callback);
    }

    public void refreshStories() {
        mCachedIsDirty = true;
    }

    private void refreshLocalDataSource(String date, List<Story> stories) {
        mLocalDataSource.deleteStories();
        mLocalDataSource.saveStories(date, stories);
    }

    private void refreshCache(String date, List<Story> stories) {

        if (mCachedStories == null)
            mCachedStories = new LinkedHashMap<>();

        if (!mCachedStories.containsKey(date))
            mCachedStories.put(date, new ArrayList<Story>());

        List<Story> list = mCachedStories.get(date);
        list.addAll(stories);
        mCachedStories.put(date, list);

        mCachedIsDirty = false;

    }
}
