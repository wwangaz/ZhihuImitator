package com.example.wangweimin.zhihuimitator.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.Model.Story;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.Net;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.api.StoryApi;
import com.example.wangweimin.zhihuimitator.util.AppUtil;

import butterknife.Bind;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 15/12/12.
 */
public class StoryDetailActivity extends BaseActivity {
    @Bind(R.id.web_view)
    WebView mWebView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.banner_image)
    ImageView mImageView;

    @Bind(R.id.banner_text)
    TextView mTextView;

//    @Bind(R.id.scroll_view)
//    NestedScrollView mScrollView;

    private String storyId;

    @Override
    protected int getLayoutId() {
        return R.layout.storyinfo_activity;
    }

    @Override
    protected void afterViews(Bundle saveInstanceState) {
        storyId = getIntent().getStringExtra(Constants.STORY_ID);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_actions, menu);
        return true;
    }

    private void getData() {
        Call<Story> call = Net.getApi(StoryApi.class).getStoryInfo(storyId);
        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Response<Story> response, Retrofit retrofit) {
                if (response.body() != null) {
                    Story story = response.body();
                    String htmlData = story.body;
                    if (story.css != null) {
                        String cssString = story.css.get(0);
                        htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssString + "\" />" + htmlData;
                    }
                    mWebView.loadData(htmlData, "text/html; charset=UTF-8", null);
                    Glide.with(thisActivity).load(story.image).into(mImageView);
                    mTextView.setText(story.title);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtil.showShortMessage(thisActivity, "获取日报数据失败");
            }
        });


    }
}
