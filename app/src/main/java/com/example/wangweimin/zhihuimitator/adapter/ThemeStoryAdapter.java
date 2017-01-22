package com.example.wangweimin.zhihuimitator.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.fragment.ThemeStoryFragment;
import com.example.wangweimin.zhihuimitator.model.Story;
import com.nineoldandroids.animation.ValueAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangweimin on 17/1/22.
 */

public class ThemeStoryAdapter extends BaseRecyclerListAdapter<Story, StoryAdapter.ViewHolder> {
    private Activity mActivity;
    private ThemeHeaderViewHolder headerViewHolder;
    private String mHeadUrl;
    private String mTitle;

    public ThemeStoryAdapter(Activity activity){
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

    public void setHeadData(String imgUrl, String title){
        mHeadUrl = imgUrl;
        mTitle = title;

        if(headerViewHolder != null){
            if(!TextUtils.isEmpty(imgUrl) && headerViewHolder.headerImage != null)
                Glide.with(mActivity).load(imgUrl).into(headerViewHolder.headerImage);
            if(!TextUtils.isEmpty(title) && headerViewHolder.headerTitle != null)
                headerViewHolder.headerTitle.setText(title);
        }
    }

    public class ThemeHeaderViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.header_image)
        ImageView headerImage;

        @Bind(R.id.header_title)
        TextView headerTitle;

        private Animation animation;

        public ThemeHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            // TODO: 17/1/22 随机漂移动画
        }
    }
}
