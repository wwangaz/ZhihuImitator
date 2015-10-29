package com.example.wangweimin.zhihuimitator.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.wangweimin.zhihuimitator.BuildConfig;

/**
 * Created by wangweimin on 15/10/29.
 */
public class ULog {
    private final static String TAG = "zhihuImitator";

    public static void d(String info) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(info)) {
                Log.d(TAG, info);
            }
        }
    }

    public static void w(String tag, String info) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(info)) {
                Log.w(TAG + "." + tag, info);
            }
        }
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
