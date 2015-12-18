package com.example.wangweimin.zhihuimitator.entity;

import java.util.List;

/**
 * Created by wangweimin on 15/10/28.
 */
public class StoryInfo extends BaseBean {
    public String body;
    public String image_source;
    public String image;
    public String share_url;
    public List<String> js;
    public List<String> css;
    public int date;
    public int id;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
