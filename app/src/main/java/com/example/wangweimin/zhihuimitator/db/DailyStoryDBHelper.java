package com.example.wangweimin.zhihuimitator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangweimin on 15/10/28.
 */
public class DailyStoryDBHelper extends SQLiteOpenHelper {
    /**
     * Create StoryInfo Table
     */
    public static final String CREATE_STORY_INFO = "create table StoryInfo ("
            + "id integer primary key, "
            + "body text, "
            + "image_source text, "
            + "image text, "
            + "share_url text)";

    public DailyStoryDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORY_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
