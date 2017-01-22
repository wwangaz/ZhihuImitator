package com.example.wangweimin.zhihuimitator.fragment;

import android.os.Bundle;

import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseFragment;
import com.example.wangweimin.zhihuimitator.view.RecyclerListView;

import butterknife.Bind;

/**
 * Created by wangweimin on 17/1/22.
 */

public class ThemeStoryFragment extends BaseFragment {

    public final static String TAG = "ThemeStoryFragment";

    public final static String THEME_ID = "theme_id";

    @Bind(R.id.theme_recycler_view)
    RecyclerListView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_story;
    }

    @Override
    protected void afterViews(Bundle savedInstanceState) {

    }

    public static ThemeStoryFragment newInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(THEME_ID, id);
        ThemeStoryFragment fragment = new ThemeStoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
