package com.example.wangweimin.zhihuimitator.dataSource.retrofit;

import java.io.Serializable;

/**
 * Created by wangweimin on 15/12/11.
 */
public class Result<D> implements Serializable {
    public boolean success;
    public String code;
    public String errorMsg;
    public D data;
}
