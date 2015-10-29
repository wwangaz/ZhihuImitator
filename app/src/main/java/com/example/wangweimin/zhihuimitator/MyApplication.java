package com.example.wangweimin.zhihuimitator;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.greenrobot.event.EventBus;

/**
 * Created by wangweimin on 15/9/16.
 */
public class MyApplication extends Application {
    public static Context mContext;
    public static SharedPreferences config;
    public static EventBus mEventBus;

    /**
     * config
     */
    public static final String EXTRA_GMID = "gmid";
    public static final String NEED_UPDATE = "need_update";
    public static final String LICENSE = "license";


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        config = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        mEventBus = EventBus.getDefault();
    }
}
