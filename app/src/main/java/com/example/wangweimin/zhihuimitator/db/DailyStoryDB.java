package com.example.wangweimin.zhihuimitator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wangweimin.zhihuimitator.entity.StoryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangweimin on 15/10/28.
 */
public class DailyStoryDB {
    /**
     * database name
     */
    public static final String DB_NAME = "StoryInfo";

    /**
     * database version
     */
    public static final int VERSION = 1;

    /**
     * private instance
     */
    private static DailyStoryDB dailyStoryDataDB;

    private SQLiteDatabase db;

    /**
     * private constructor
     */
    private DailyStoryDB(Context context) {
        DailyStoryDBHelper dbHelper = new DailyStoryDBHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * get instance of DailyStoryDB
     */
    public synchronized static DailyStoryDB getInstance(Context context){
        if(dailyStoryDataDB == null){
            dailyStoryDataDB = new DailyStoryDB(context);
        }
        return dailyStoryDataDB;
    }

    /**
     * save StoryInfo
     * @param storyInfo
     */
    public void saveStoryInfo(StoryInfo storyInfo){
        if(storyInfo != null){
            ContentValues values = new ContentValues();
            values.put("body", storyInfo.body);
            values.put("image", storyInfo.image);
            values.put("image_source", storyInfo.image_source);
            values.put("id", storyInfo.id);
            values.put("date", storyInfo.date);
            values.put("share_url", storyInfo.share_url);
            db.insert(DB_NAME, null, values);
        }
    }

    /**
     * get StoryInfo list
     */
    public List<StoryInfo> loadStoryInfo(int date){
        List<StoryInfo> list = new ArrayList<>();
        Cursor cursor = db.query(DB_NAME, null, "date = ?", new String[]{String.valueOf(date)}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                StoryInfo storyInfo = new StoryInfo();
                storyInfo.setBody(cursor.getString(cursor.getColumnIndex("body")));
                storyInfo.setDate(cursor.getInt(cursor.getColumnIndex("date")));
                storyInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                storyInfo.setImage_source(cursor.getString(cursor.getColumnIndex("image_source")));
                storyInfo.setImage(cursor.getString(cursor.getColumnIndex("image")));
                storyInfo.setShare_url(cursor.getString(cursor.getColumnIndex("share_url")));
                list.add(storyInfo);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
}
