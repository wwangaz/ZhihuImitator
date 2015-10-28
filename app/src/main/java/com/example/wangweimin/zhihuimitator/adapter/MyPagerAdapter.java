package com.example.wangweimin.zhihuimitator.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.wangweimin.zhihuimitator.fragment.SuperAwesomeCardFragment;

/**
 * Created by wangweimin on 15/8/5.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"分类", "主页", "热门推荐", "热门收藏", "本月热榜", "今日热榜", "专栏", "随机"};

    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        return SuperAwesomeCardFragment.newInstance(position);
    }

}
