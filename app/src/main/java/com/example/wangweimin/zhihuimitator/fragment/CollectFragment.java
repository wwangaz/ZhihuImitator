package com.example.wangweimin.zhihuimitator.fragment;

import android.os.Bundle;

import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.adapter.StoryAdapter;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.base.BaseFragment;
import com.example.wangweimin.zhihuimitator.dataSource.StoryDataSource;
import com.example.wangweimin.zhihuimitator.dataSource.StoryRepository;
import com.example.wangweimin.zhihuimitator.util.AppUtil;
import com.example.wangweimin.zhihuimitator.view.RecyclerListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by wangweimin on 16/8/23.
 */

public class CollectFragment extends BaseFragment {
    public final static String TAG = "CollectFragment";

    @Bind(R.id.collect_recycler_view)
    RecyclerListView mRecyclerView;

    private StoryAdapter mAdapter;
    private StoryRepository mRepository;
    private int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void afterViews(Bundle savedInstanceState) {
        mAdapter = new StoryAdapter();
        mAdapter.setActivity((BaseActivity) getActivity());

        mRecyclerView.setDecorate(false);
        mRecyclerView.setAdapter(mAdapter);

        mRepository = StoryRepository.getInstance(getContext());

        mRecyclerView.setOnRecyclerViewScrollBottomListener(new RecyclerListView.OnRecyclerViewScrollBottomListener() {
            @Override
            public void requestNextPage() {
                page++;
                getData();
            }
        });
        getData();
    }

    private void getData() {
        mRepository.getFavoriteStories(new StoryDataSource.LoadStoriesCallback() {
            @Override
            public void onStoriesLoaded(List<Story> stories) {
                mAdapter.refreshViewByAddData(stories);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }, page);
    }
}
