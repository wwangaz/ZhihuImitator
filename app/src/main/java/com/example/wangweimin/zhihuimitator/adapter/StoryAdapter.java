package com.example.wangweimin.zhihuimitator.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.view.BannerViewPager;
import com.example.wangweimin.zhihuimitator.view.IndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangweimin on 15/12/11.
 */
public class StoryAdapter extends BaseRecyclerListAdapter<Story, StoryAdapter.ViewHolder> {

    private BaseActivity mActivity;

    private HeadViewHolder mHeadViewHolder;

    private List<Story> mBannerList;

    public StoryAdapter(BaseActivity activity) {
        mActivity = activity;
        mBannerList = new ArrayList<>();
    }

    public void setBannerList(List<Story> bannerList) {
        if (mHeadViewHolder != null && mHeadViewHolder.mCircularAdapter != null)
            mHeadViewHolder.mCircularAdapter.setBannerList(bannerList);
        else mBannerList = bannerList;
    }

    @Override
    protected HeadViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_layout, viewGroup, false);
        mHeadViewHolder = new HeadViewHolder(view);
        return mHeadViewHolder;
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.story_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder viewHolder, int position) {
        Story story = getData().get(position);
        if (story != null) {
            if (story.images != null && story.images.size() > 0)
                Glide.with(mActivity).load(story.images.get(0)).into(viewHolder.storyImage);
            viewHolder.storyTitle.setText(story.title);
        }

    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.banner_pager)
        BannerViewPager mBannerViewPager;

        @Bind(R.id.banner_indicator)
        IndicatorView mIndicatorView;

        private CircularPageAdapter mCircularAdapter;

        private HeadViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            mCircularAdapter = new CircularPageAdapter(mBannerViewPager, mActivity, mIndicatorView);
            mBannerViewPager.setAdapter(mCircularAdapter);
            if (mBannerList != null && mBannerList.size() > 0)
                mCircularAdapter.setBannerList(mBannerList);

            try {
                mBannerViewPager.stopLoopingBanner();
                mBannerViewPager.setOffscreenPageLimit(3);
                mBannerViewPager.setCurrentItem(1, false);
                mBannerViewPager.startLoopingBanner();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.story_image)
        ImageView storyImage;

        @Bind(R.id.story_title)
        TextView storyTitle;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
