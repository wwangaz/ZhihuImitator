package com.example.wangweimin.zhihuimitator.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangweimin on 17/1/22.
 */

public class Themes implements Serializable {
    public long color;
    public String thumbnail;
    public String description;
    public String name;
    public String id;

    public static class ThemesResult implements Serializable {
        public int limit;
        public List<Themes> subscribed;
        public List<Themes> others;

    }
}
