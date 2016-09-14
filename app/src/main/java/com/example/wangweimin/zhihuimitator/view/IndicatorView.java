package com.example.wangweimin.zhihuimitator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.adapter.CircularPageAdapter;
import com.example.wangweimin.zhihuimitator.util.AppUtil;

/**
 * Created by wangweimin on 16/8/18.
 */

public class IndicatorView extends LinearLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private Context mContext;
    private int mSelectedIndicator = R.drawable.indicator_selected;
    private int mUnSelectedIndicator = R.drawable.indicator_unselected;

    public IndicatorView(Context context) {
        super(context, null);
        mContext = context;
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOrientation(HORIZONTAL);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView, defStyleAttr, 0);
        mSelectedIndicator = a.getResourceId(R.styleable.IndicatorView_selected_indicator, mSelectedIndicator);
        mUnSelectedIndicator = a.getResourceId(R.styleable.IndicatorView_unselected_indicator, mUnSelectedIndicator);
        a.recycle();
    }

    public void setViewPagerInfo(ViewPager viewPager, boolean isCircularPagerAdapter) {
        this.mViewPager = viewPager;
        if (!isCircularPagerAdapter)
            mViewPager.setOnPageChangeListener(this);
        renderChildView();
    }

    public void refreshView(){
        renderChildView();
    }

    private void renderChildView() {
        this.removeAllViews();
        PagerAdapter mAdapter = mViewPager.getAdapter();
        int pagerCount = 0;
        if (mAdapter != null) {
            if (mAdapter instanceof CircularPageAdapter) {
                int count = mAdapter.getCount();
                //因为CircularPageAdapter中加了两个页面实现循环效果
                pagerCount = mAdapter.getCount() - (count == 1 ? 0 : 2);
            } else
                pagerCount = mAdapter.getCount();
        }

        if (pagerCount >= 1) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = AppUtil.convertDpToPx(4);

            for (int i = 0; i < pagerCount; i++) {
                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(params);
                this.addView(imageView);
            }
            selectPage(0);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void selectPage(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            if (this.getChildAt(i) instanceof ImageView)
                ((ImageView) this.getChildAt(i)).setImageResource(position == i ? mSelectedIndicator : mUnSelectedIndicator);
        }
    }
}
