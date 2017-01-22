package com.example.wangweimin.zhihuimitator.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawableResource;
import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.Net;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.api.StoryApi;
import com.example.wangweimin.zhihuimitator.model.Image;
import com.example.wangweimin.zhihuimitator.util.AppUtil;
import com.nineoldandroids.view.ViewHelper;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 17/1/20.
 */

public class SplashActivity extends BaseActivity {

    private final static int DURATION = 2000;

    @Bind(R.id.splash_image)
    ImageView imageView;

    @Bind(R.id.splash_title)
    TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void afterViews(Bundle saveInstanceState) {

        // TODO: 17/1/22 加载对应尺寸的首页图
        Net.getApi(StoryApi.class).getStartImage(Constants.SCREEN_RESOLUTION).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Response<Image> response, Retrofit retrofit) {
                if (response.body() != null && !TextUtils.isEmpty(response.body().img))
                    Glide.with(mContext).load(response.body().img).override(AppUtil.getScreenWidth(), AppUtil.getScreenHeight()).centerCrop().into(imageView);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        // TODO: 17/1/20 图片的缓存，交给Glide处理

        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 1.3f);
        animator.setTarget(imageView);
        animator.setDuration(DURATION).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewHelper.setScaleX(imageView, (Float) animation.getAnimatedValue());
                ViewHelper.setScaleY(imageView, (Float) animation.getAnimatedValue());

            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                pushView(MainActivity.class, null);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
