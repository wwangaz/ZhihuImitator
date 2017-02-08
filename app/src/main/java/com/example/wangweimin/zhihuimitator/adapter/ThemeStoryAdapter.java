package com.example.wangweimin.zhihuimitator.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.activity.EditorListActivity;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.model.Editor;
import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.util.AppUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangweimin on 17/1/22.
 */

public class ThemeStoryAdapter extends BaseRecyclerListAdapter<Story, StoryAdapter.ViewHolder> {
    private Activity mActivity;
    private ThemeHeaderViewHolder headerViewHolder;

    public ThemeStoryAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.theme_story_header, viewGroup, false);
        headerViewHolder = new ThemeHeaderViewHolder(view);
        return headerViewHolder;
    }

    @Override
    protected StoryAdapter.ViewHolder onCreateItemViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.story_list_item, viewGroup, false);
        return new StoryAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(StoryAdapter.ViewHolder viewHolder, int position) {
        Story story = getData().get(position);
        if (story != null) {
            if (story.images != null && story.images.size() > 0)
                Glide.with(mActivity).load(story.images.get(0)).into(viewHolder.storyImage);
            viewHolder.storyTitle.setText(story.title);
        }
    }

    public void setHeadData(String imgUrl, String title, final ArrayList<Editor> editors) {
        if (headerViewHolder != null) {
            if (!TextUtils.isEmpty(imgUrl) && headerViewHolder.headerImage != null)
                Glide.with(mActivity).load(imgUrl).centerCrop().override(headerViewHolder.headerImage.getWidth(), headerViewHolder.headerImage.getHeight()).into(headerViewHolder.headerImage);
            if (!TextUtils.isEmpty(title) && headerViewHolder.headerTitle != null)
                headerViewHolder.headerTitle.setText(title);
            // TODO: 17/2/7 设置编辑列表
            headerViewHolder.editorLayout.removeAllViews();
            for (Editor editor : editors) {
                final ImageView imageView = new ImageView(mActivity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AppUtil.convertDpToPx(20), AppUtil.convertDpToPx(20));
                params.gravity = Gravity.CENTER;
                params.leftMargin = AppUtil.convertDpToPx(5);

                Glide.with(mActivity).load(editor.avatar).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mActivity.getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(roundedBitmapDrawable);
                    }
                });
                headerViewHolder.editorLayout.addView(imageView, params);
            }
            headerViewHolder.editorLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, EditorListActivity.class);
                    intent.putExtra(EditorListActivity.EDITORS, editors);
                    intent.putExtra(EditorListActivity.TITLE, "主编");
                    mActivity.startActivity(intent);
                }
            });
            headerViewHolder.animator.start();
        }
    }

    public class ThemeHeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.header_image)
        ImageView headerImage;

        @Bind(R.id.header_title)
        TextView headerTitle;

        @Bind(R.id.editor_layout)
        LinearLayout editorLayout;

        private ValueAnimator animator;
        private float animationFraction;

        public ThemeHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // TODO: 17/1/22 随机漂移动画
            animator = ValueAnimator.ofFloat(1.0f, 1.1f);
            animator.setTarget(headerImage);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float imageWidth = headerImage.getWidth();
                    float imageHeight = headerImage.getHeight();
                    ViewHelper.setScaleX(headerImage, (Float) animation.getAnimatedValue());
                    ViewHelper.setScaleY(headerImage, (Float) animation.getAnimatedValue());
                    ViewHelper.setPivotX(headerImage, imageWidth * animationFraction);
                    ViewHelper.setPivotX(headerImage, imageHeight * animationFraction);
                }
            });
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setDuration(5000);

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    animationFraction = new Random().nextFloat();
                }
            });
        }
    }
}
