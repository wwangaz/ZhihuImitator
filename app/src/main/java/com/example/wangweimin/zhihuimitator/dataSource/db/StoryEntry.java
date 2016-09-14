package com.example.wangweimin.zhihuimitator.dataSource.db;

import android.provider.BaseColumns;

/**
 * Created by wangweimin on 16/8/17.
 */
public abstract class StoryEntry implements BaseColumns {
    public static final String COLUMN_NAME_ENTRY_ID = "id";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_BODY = "body";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_IMAGE_SOURCE = "image_source";
    public static final String COLUMN_NAME_IMAGE = "image";
    public static final String COLUMN_NAME_SHARE_URL = "share_url";
    public static final String COLUMN_NAME_FAVORITE = "favorite";
    public static final String COLUMN_NAME_TOP = "top";
}


