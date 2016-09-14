package com.example.wangweimin.zhihuimitator.dataSource.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.Model.Story;
import com.example.wangweimin.zhihuimitator.dataSource.StoryDataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by wangweimin on 15/10/28.
 */
public class StoriesDB implements StoryDataSource {
    /**
     * database name
     */
    public static final String DB_NAME = "Story";

    /**
     * database version
     */
    private static final int VERSION = 1;

    /**
     * private instance
     */
    private static StoriesDB dailyStoryDataDB;

    private StoriesDBHelper mDBHelper;

    private Calendar calendar = Calendar.getInstance();

    /**
     * private constructor
     */
    private StoriesDB(Context context) {
        mDBHelper = new StoriesDBHelper(context, DB_NAME, null, VERSION);
    }

    /**
     * get instance of StoriesDB
     */
    public synchronized static StoriesDB getInstance(Context context) {
        if (dailyStoryDataDB == null) {
            dailyStoryDataDB = new StoriesDB(context);
        }
        return dailyStoryDataDB;
    }

    /**
     * save Story
     *
     * @param stories
     */
    private void saveStory(String date, List<Story> stories) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        for (Story story : stories) {
            if (story != null) {
                ContentValues values = new ContentValues();
                values.put("body", story.body);
                values.put("title", story.title);
                if (story.images.size() > 0)
                    values.put("image", story.images.get(0));
                values.put("image_source", story.image_source);
                values.put("id", story.id);
                values.put("date", date);
                values.put("share_url", story.share_url);
                values.put("favorite", story.favorite);
                values.put("top", story.top);
                db.insert(DB_NAME, null, values);
            }
        }
        db.close();
    }

    private void updateStoryById(String id) {
        checkNotNull(id);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (!"".equals(id)) {
            ContentValues values = new ContentValues();
            values.put("favorite", true);

            String where = StoryEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
            String[] whereArgs = {id};

            db.update(DB_NAME, values, where, whereArgs);
        }
    }

    /**
     * get Story list
     */
    private List<Story> loadStoryByDate(String date) {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        List<Story> list = new ArrayList<>();

        String[] projects = {
                StoryEntry.COLUMN_NAME_ENTRY_ID,
                StoryEntry.COLUMN_NAME_DATE,
                StoryEntry.COLUMN_NAME_BODY,
                StoryEntry.COLUMN_NAME_TITLE,
                StoryEntry.COLUMN_NAME_IMAGE_SOURCE,
                StoryEntry.COLUMN_NAME_IMAGE,
                StoryEntry.COLUMN_NAME_SHARE_URL,
                StoryEntry.COLUMN_NAME_FAVORITE,
                StoryEntry.COLUMN_NAME_TOP
        };

        String selection = StoryEntry.COLUMN_NAME_DATE + " LIKE ?";
        String[] selectionArgs = {date};

        Cursor cursor = db.query(DB_NAME, projects, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Story storyInfo = new Story();
                storyInfo.body = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_BODY)));
                storyInfo.title = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_TITLE)));
                storyInfo.date = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_DATE)));
                storyInfo.id = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_ENTRY_ID)));
                storyInfo.image_source = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_IMAGE_SOURCE)));
                storyInfo.image = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_IMAGE)));
                storyInfo.share_url = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_SHARE_URL)));
                storyInfo.favorite = (cursor.getInt(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_FAVORITE)) == 1);
                storyInfo.top = (cursor.getInt(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_TOP)) == 1);
                list.add(storyInfo);
            }
        }

        if (cursor != null)
            cursor.close();
        return list;
    }


    @NonNull
    private List<Story> loadStoryByFavorite(int page) {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        List<Story> list = new ArrayList<>();
        String[] projects = {
                StoryEntry.COLUMN_NAME_ENTRY_ID,
                StoryEntry.COLUMN_NAME_DATE,
                StoryEntry.COLUMN_NAME_BODY,
                StoryEntry.COLUMN_NAME_TITLE,
                StoryEntry.COLUMN_NAME_IMAGE_SOURCE,
                StoryEntry.COLUMN_NAME_IMAGE,
                StoryEntry.COLUMN_NAME_SHARE_URL,
                StoryEntry.COLUMN_NAME_FAVORITE,
                StoryEntry.COLUMN_NAME_TOP
        };

        String selection = StoryEntry.COLUMN_NAME_FAVORITE + " LIKE ?";
        String[] selectionArgs = {"1"};

        Cursor cursor = db.query(DB_NAME, projects, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Story storyInfo = new Story();
                storyInfo.body = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_BODY)));
                storyInfo.title = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_TITLE)));
                storyInfo.date = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_DATE)));
                storyInfo.id = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_ENTRY_ID)));
                storyInfo.image_source = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_IMAGE_SOURCE)));
                storyInfo.image = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_IMAGE)));
                storyInfo.share_url = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_SHARE_URL)));
                storyInfo.favorite = (cursor.getInt(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_FAVORITE)) == 1);
                storyInfo.top = (cursor.getInt(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_TOP)) == 1);
                list.add(storyInfo);
            }
        }

        if (cursor != null)
            cursor.close();

        if (list.size() > page * Constants.PAGE_LIMIT)
            return list.subList(page * Constants.PAGE_LIMIT, (page + 1) * Constants.PAGE_LIMIT);
        else return new ArrayList<>();
    }

    private List<Story> loadStoryByTop() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        List<Story> list = new ArrayList<>();
        String[] projects = {
                StoryEntry.COLUMN_NAME_ENTRY_ID,
                StoryEntry.COLUMN_NAME_DATE,
                StoryEntry.COLUMN_NAME_BODY,
                StoryEntry.COLUMN_NAME_TITLE,
                StoryEntry.COLUMN_NAME_IMAGE_SOURCE,
                StoryEntry.COLUMN_NAME_IMAGE,
                StoryEntry.COLUMN_NAME_SHARE_URL,
                StoryEntry.COLUMN_NAME_FAVORITE,
                StoryEntry.COLUMN_NAME_TOP
        };

        String selection = StoryEntry.COLUMN_NAME_TOP + " LIKE ?";
        String[] selectionArgs = {"1"};

        Cursor cursor = db.query(DB_NAME, projects, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Story storyInfo = new Story();
                storyInfo.body = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_BODY)));
                storyInfo.title = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_TITLE)));
                storyInfo.date = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_DATE)));
                storyInfo.id = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_ENTRY_ID)));
                storyInfo.image_source = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_IMAGE_SOURCE)));
                storyInfo.image = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_IMAGE)));
                storyInfo.share_url = (cursor.getString(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_SHARE_URL)));
                storyInfo.favorite = (cursor.getInt(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_FAVORITE)) == 1);
                storyInfo.top = (cursor.getInt(cursor.getColumnIndexOrThrow(StoryEntry.COLUMN_NAME_TOP)) == 1);
                list.add(storyInfo);
            }
        }

        if (cursor != null)
            cursor.close();

        return list;
    }

    @Override
    public void getFavoriteStories(LoadStoriesCallback callback, int page) {
        List<Story> list = loadStoryByFavorite(page);
        if (list.size() > 0)
            callback.onStoriesLoaded(list);
        else callback.onDataNotAvailable();

    }

    @Override
    public void getStoriesByDate(String date, LoadStoriesCallback callback) {
        List<Story> list = loadStoryByDate(date);
        if (list != null && list.size() > 0)
            callback.onStoriesLoaded(list);
        else callback.onDataNotAvailable();
    }

    @Override
    public void getLatestStories(LoadStoriesCallback callback) {

    }

    @Override
    public void saveStories(String date, List<Story> stories) {
        saveStory(date, stories);
    }

    @Override
    public void deleteStories() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete(DB_NAME, null, null);
        db.close();
    }

    @Override
    public void collectStory(String id) {
        updateStoryById(id);
    }

    @Override
    public void getTopStories(LoadStoriesCallback callback) {
        List<Story> list = loadStoryByTop();
        if (list != null && list.size() > 0)
            callback.onStoriesLoaded(list);
        else callback.onDataNotAvailable();
    }
}
