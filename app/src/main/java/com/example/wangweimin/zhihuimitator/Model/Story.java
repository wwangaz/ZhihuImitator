package com.example.wangweimin.zhihuimitator.model;

import com.example.wangweimin.zhihuimitator.base.BaseBean;

import java.util.List;

/**
 * Created by wangweimin on 15/10/28.
 */
public class Story extends BaseBean {
    public String date;
    public String id;
    public List<String> images;
    public String title;
    public String body;
    public String image_source;
    public String image;
    public String share_url;
    public List<String> js;
    public List<String> css;
    public List<Editor> recommenders;
    public boolean favorite;
    public boolean top;
    public int type;
    public boolean read;

    public class StoryResult {
        public String date;
        public List<Story> stories;
        public List<Story> top_stories;
    }
}
