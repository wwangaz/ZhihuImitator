package com.example.wangweimin.zhihuimitator.adapter;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.activity.StoryDetailActivity;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.view.IndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangweimin on 16/8/19.
 */

public class CircularPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private int mFakeSize;
    private List<Story> mBannerList;
    private SparseArray<View> mViewSparseArray;
    private IndicatorView mIndicatorView;
    private ViewPager mViewPager;
    private BaseActivity mActivity;

    private LayoutInflater mLayoutInflater;

    public void setBannerList(List<Story> bannerList) {
        mViewSparseArray.clear();
        mBannerList = bannerList;
        int realSize = bannerList.size();
        mFakeSize = realSize + 2;

        if (mIndicatorView != null)
            mIndicatorView.refreshView();

        this.notifyDataSetChanged();
    }

    public CircularPageAdapter(final ViewPager pager, BaseActivity activity, final IndicatorView indicatorView) {
        mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        mBannerList = new ArrayList<>();
        mViewSparseArray = new SparseArray<>();
        mIndicatorView = indicatorView;
        mViewPager = pager;

        if (indicatorView != null)
            indicatorView.setViewPagerInfo(pager, true);

        pager.setOnPageChangeListener(this);

    }

    @Override
    public int getCount() {
        return mFakeSize == 3 ? 1 : mFakeSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (mIndicatorView != null) {
            int realCount = mFakeSize - 2;
            int realPosition = (position - 1) % realCount;
            if (realPosition < 0) realPosition += realCount;

            mIndicatorView.selectPage(realPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            int pageCount = getCount();
            int currentItem = mViewPager.getCurrentItem();
            if (currentItem == 0)
                mViewPager.setCurrentItem(pageCount - 2, false);
            else if (currentItem == pageCount - 1)
                mViewPager.setCurrentItem(1, false);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realCount = mFakeSize - 2;

        int realPosition = (position - 1) % realCount;
        if (realPosition < 0) realPosition += realCount;

        View view;
        if (mViewSparseArray.indexOfKey(position) >= 0)
            view = mViewSparseArray.get(position);
        else {
            final Story story = mBannerList.get(realPosition);
            view = mLayoutInflater.inflate(R.layout.banner_item, container, false);
            if (story != null) {
                ImageView imageView = (ImageView) view.findViewById(R.id.banner_image);
                Glide.with(container.getContext()).load(story.image).into(imageView);

                TextView textView = (TextView) view.findViewById(R.id.banner_text);
                textView.setText(story.title);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.STORY_ID, story.id);
                        mActivity.pushView(StoryDetailActivity.class, bundle, true);
                    }
                });
                mViewSparseArray.put(position, view);
            }
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewSparseArray.get(position));
    }

    /**
     * 每次notifyDataSetChanged后调用
     * see http://stackoverflow.com/questions/7263291/viewpager-pageradapter-not-updating-the-view
     * http://stackoverflow.com/questions/10849552/android-viewpager-cant-update-dynamically
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
