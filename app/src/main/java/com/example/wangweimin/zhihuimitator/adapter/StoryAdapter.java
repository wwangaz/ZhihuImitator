package com.example.wangweimin.zhihuimitator.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.activity.BaseActivity;
import com.example.wangweimin.zhihuimitator.entity.Story;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangweimin on 15/12/11.
 */
public class StoryAdapter extends BaseRecyclerListAdapter<Story, StoryAdapter.ViewHolder> {
    private BaseActivity mActivity;

    public void setActivity(BaseActivity activity){
        mActivity = activity;
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.story_list_item,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder viewHolder, int position) {
        Story story = getData().get(position);
        if(story != null){
            Glide.with(mActivity).load(story.images.get(0)).into(viewHolder.storyImage);
            viewHolder.storyTitle.setText(story.title);
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.story_image)
        ImageView storyImage;

        @Bind(R.id.story_title)
        TextView storyTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
