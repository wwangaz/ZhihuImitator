package com.example.wangweimin.zhihuimitator.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.api.StoryApi;
import com.example.wangweimin.zhihuimitator.entity.StoryInfo;
import com.example.wangweimin.zhihuimitator.retrofit.Net;
import com.example.wangweimin.zhihuimitator.util.AppUtil;

import butterknife.Bind;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 15/12/12.
 */
public class StoryInfoActivity extends BaseActivity {
    @Bind(R.id.web_view)
    WebView mWebView;

    private int storyId;

    @Override
    protected int getLayoutId() {
        return R.layout.storyinfo_activity;
    }

    @Override
    protected void afterViews(Bundle saveInstanceState) {
        storyId = getIntent().getIntExtra(Constants.STORY_ID, 0);

        getData();
    }

    public void getData() {
        Call<StoryInfo> call = Net.getApi(StoryApi.class).getStoryInfo(storyId);
        call.enqueue(new Callback<StoryInfo>() {
            @Override
            public void onResponse(Response<StoryInfo> response, Retrofit retrofit) {
                String htmlData = response.body().body;
                if (response.body().css != null) {
                    String cssString = response.body().css.get(0);
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\""+cssString+"\" />" + htmlData;
                }
                mWebView.loadData(htmlData, "text/html; charset=UTF-8", null);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtil.showShortMessage(thisActivity, "获取日报数据失败");
            }
        });
    }
}
