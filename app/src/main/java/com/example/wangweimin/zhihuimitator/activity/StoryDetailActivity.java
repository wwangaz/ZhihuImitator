package com.example.wangweimin.zhihuimitator.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.Net;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.api.StoryApi;
import com.example.wangweimin.zhihuimitator.model.Editor;
import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 15/12/12.
 */
public class StoryDetailActivity extends BaseActivity {

    public final static String SHOW_HEAD = "showHead";

    @Bind(R.id.web_view)
    WebView mWebView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.banner_image)
    ImageView mImageView;

    @Bind(R.id.banner_text)
    TextView mTextView;

    @Bind(R.id.adviser_layout)
    LinearLayout mAdvisorLayout;

    @Bind(R.id.banner_layout)
    RelativeLayout mBannerLayout;

    @Bind(R.id.img_layout)
    LinearLayout mImgLayout;

    private String storyId;
    private boolean showHead;

    @Override
    protected int getLayoutId() {
        return R.layout.storyinfo_activity;
    }

    @Override
    protected void afterViews(Bundle saveInstanceState) {
        storyId = getIntent().getStringExtra(Constants.STORY_ID);
        showHead = getIntent().getBooleanExtra(SHOW_HEAD, true);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        if(!showHead){
            mBannerLayout.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.topMargin = 0;
            mWebView.setLayoutParams(params);
        }

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                popView();
                return true;
            // TODO: 17/2/8 分享收藏评论点赞功能
        }
        return super.onOptionsItemSelected(item);
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
                    if(story.recommenders != null && !story.recommenders.isEmpty() && !showHead){
                        renderRecommenderLayout(story.recommenders);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtil.showShortMessage(thisActivity, "获取日报数据失败");
            }
        });
    }

    private void renderRecommenderLayout(final List<Editor> advisers){
        mAdvisorLayout.setVisibility(View.VISIBLE);
        for(Editor editor : advisers){
            final ImageView imageView = new ImageView(thisActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AppUtil.convertDpToPx(30), AppUtil.convertDpToPx(30));
            params.gravity = Gravity.CENTER;
            params.leftMargin = AppUtil.convertDpToPx(10);

            Glide.with(thisActivity).load(editor.avatar).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                    roundedBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(roundedBitmapDrawable);
                }
            });
            mImgLayout.addView(imageView, params);
        }
        mAdvisorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, EditorListActivity.class);
                intent.putExtra(EditorListActivity.EDITORS, new ArrayList<>(advisers));
                intent.putExtra(EditorListActivity.TITLE, "推荐者");
            }
        });
    }
}
