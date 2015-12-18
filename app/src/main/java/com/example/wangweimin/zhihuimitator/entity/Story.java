package com.example.wangweimin.zhihuimitator.entity;

import java.util.List;

/**
 * Created by wangweimin on 15/10/28.
 */
public class Story extends BaseBean {
    public List<String> images;
    public int type;
    public int id;
    public String title;

    public class StoryResult {
        public int date;
        public List<Story> stories;
        public List<Story> top_stories;
    }
}
