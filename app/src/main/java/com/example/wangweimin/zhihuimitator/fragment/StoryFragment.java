package com.example.wangweimin.zhihuimitator.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.activity.StoryDetailActivity;
import com.example.wangweimin.zhihuimitator.adapter.StoryAdapter;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.base.BaseFragment;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.dataSource.StoryDataSource;
import com.example.wangweimin.zhihuimitator.dataSource.StoryRepository;
import com.example.wangweimin.zhihuimitator.util.AppUtil;
import com.example.wangweimin.zhihuimitator.view.RecyclerListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;

/**
 * Created by wangweimin on 16/8/22.
 */

public class StoryFragment extends BaseFragment {

    public static String TAG = "StoryFragment";

    @Bind(R.id.main_recycler_view)
    RecyclerListView mListView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private BaseActivity mActivity;
    private StoryAdapter mAdapter;
    private StoryRepository repository;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_story;
    }

    @Override
    protected void afterViews(Bundle savedInstanceState) {

        mActivity = (BaseActivity) getActivity();
        repository = StoryRepository.getInstance(getContext());

        mAdapter = new StoryAdapter(mActivity);
        mAdapter.setHasHeader(true);

        mListView.setAdapter(mAdapter);
        mListView.setDecorate(false);

        mAdapter.setOnRecyclerViewItemClickListener(new BaseRecyclerListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Story story = mAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.STORY_ID, story.id);
                mActivity.pushView(StoryDetailActivity.class, bundle);
            }
        });

        mListView.setOnRecyclerViewScrollBottomListener(new RecyclerListView.OnRecyclerViewScrollBottomListener() {
            @Override
            public void requestNextPage() {
                getNextData();
            }
        });

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();
    }


    private void getData() {

        repository.getLatestStories(new StoryDataSource.LoadStoriesCallback() {
            @Override
            public void onStoriesLoaded(List<Story> stories) {
                mAdapter.refreshViewByReplaceData(stories);
                mListView.renderViewByResult(false, stories.size(), stories.isEmpty());
                dismissProgress();
            }

            @Override
            public void onDataNotAvailable() {
                AppUtil.showShortMessage(mActivity, "获取最新日报失败");
            }
        });

        repository.getTopStories(new StoryDataSource.LoadStoriesCallback() {
            @Override
            public void onStoriesLoaded(List<Story> stories) {
                mAdapter.setBannerList(stories);
            }

            @Override
            public void onDataNotAvailable() {
                AppUtil.showShortMessage(mActivity, "获取热门日报失败");
            }
        });

    }

    private void getNextData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        final String date = simpleDateFormat.format(calendar.getTime());
        repository.getStoriesByDate(date, new StoryDataSource.LoadStoriesCallback() {
            @Override
            public void onStoriesLoaded(List<Story> stories) {
                mAdapter.refreshViewByAddData(stories);
                mListView.renderViewByResult(false, stories.size(), stories.isEmpty());
            }

            @Override
            public void onDataNotAvailable() {
                AppUtil.showShortMessage(mActivity, "获取" + date + "日报失败");
            }
        });

    }

    private void dismissProgress() {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
