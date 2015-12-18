package com.example.wangweimin.zhihuimitator.entity;

import com.example.wangweimin.zhihuimitator.MyException;
import com.example.wangweimin.zhihuimitator.util.AppUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BaseBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static boolean isRequestSuccess(JSONObject json) throws MyException {
        if (json != null && json.has("success")) {
            if (AppUtil.getJsonBooleanValue(json, "success", false)) {
                return true;
            } else {
                int code = AppUtil.getJsonIntegerValue(json, "code");
//                AppUtil.checkTokenTimeout(code);
                throw new MyException(code, AppUtil.getJsonStringValue(json, "errorMsg"));
            }
        } else {
            ErrorType errorType = ErrorType.getErrorType(ErrorType.NETWORK_REQUEST_ERROR);
            throw new MyException(errorType.getErrorCode(), errorType.getErrorBody());
        }
    }

    protected static <T extends BaseBean> List<T> fromJsonToBeanList(String arrayStr, final Class<T> classOfT) {
        List<T> resultList;
        Type tType = new ParameterizedType() {

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{classOfT};
            }
        };
        try {
            resultList = new Gson().fromJson(arrayStr, tType);
            if (resultList == null) {
                resultList = new ArrayList<>();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    protected static <T extends BaseBean> T fromJsonToBean(String jsonStr, final Class<T> classOfT) {
        T result = null;
        try {
            result = new Gson().fromJson(jsonStr, classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }
}
