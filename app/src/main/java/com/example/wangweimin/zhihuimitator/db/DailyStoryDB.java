package com.example.wangweimin.zhihuimitator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wangweimin on 15/10/28.
 */
public class DailyStoryDB {
    /**
     * database name
     */
    public static final String DB_NAME = "strories.db";

    /**
     * database version
     */
    public static final int VERSION = 1;

    /**
     * private instance
     */
    private DailyStoryDB dailyStoryDataDB;

    private SQLiteDatabase db;

    /**
     * private constructor
     */
    private DailyStoryDB(Context context) {

    }

}
