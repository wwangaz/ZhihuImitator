package com.example.wangweimin.zhihuimitator.dataSource.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangweimin on 15/10/28.
 */
public class StoriesDBHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    /**
     * Create StoryInfo Table
     */
    private static final String CREATE_STORY_INFO = " CREATE TABLE " + StoriesDB.DB_NAME + " ("
            + StoryEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + " PRIMARY KEY,"
            + StoryEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP
            + StoryEntry.COLUMN_NAME_BODY + TEXT_TYPE + COMMA_SEP
            + StoryEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
            + StoryEntry.COLUMN_NAME_IMAGE_SOURCE + TEXT_TYPE + COMMA_SEP
            + StoryEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP
            + StoryEntry.COLUMN_NAME_SHARE_URL + TEXT_TYPE + COMMA_SEP
            + StoryEntry.COLUMN_NAME_FAVORITE + INTEGER_TYPE + COMMA_SEP
            + StoryEntry.COLUMN_NAME_TOP + INTEGER_TYPE
            + " )";

    public StoriesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
