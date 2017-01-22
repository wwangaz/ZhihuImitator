package com.example.wangweimin.zhihuimitator.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangweimin on 17/1/22.
 */

public class ThemesDetail implements Serializable {
    public List<Story> stories;
    public String description;
    public String background;
    public String name;
    public long color;
    public String image;
    public List<Editor> editors;
    public String image_source;
}
