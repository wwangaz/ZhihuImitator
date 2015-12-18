package com.example.wangweimin.zhihuimitator.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.adapter.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.adapter.StoryAdapter;
import com.example.wangweimin.zhihuimitator.api.StoryApi;
import com.example.wangweimin.zhihuimitator.entity.Story;
import com.example.wangweimin.zhihuimitator.retrofit.Net;
import com.example.wangweimin.zhihuimitator.util.AppUtil;
import com.example.wangweimin.zhihuimitator.view.RecyclerListView;

import java.util.List;

import butterknife.Bind;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 15/10/29.
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.main_recycler_view)
    RecyclerListView mListView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Bind(R.id.main_tool_bar)
    Toolbar mToolBar;

    private StoryAdapter mAdapter;
    private int date;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterViews(Bundle saveInstanceState) {
        setSupportActionBar(mToolBar);
        mAdapter = new StoryAdapter();

        mAdapter.setActivity(thisActivity);
        mListView.setAdapter(mAdapter);

        mAdapter.setOnRecyclerViewItemClickListener(new BaseRecyclerListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Story story = mAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.STORY_ID, story.id);
                pushView(StoryInfoActivity.class, bundle);
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

    public void getData() {
        Call<Story.StoryResult> call = Net.getApi(StoryApi.class).latestStories();
        call.enqueue(new Callback<Story.StoryResult>() {
                         @Override
                         public void onResponse(Response<Story.StoryResult> response, Retrofit retrofit) {
                             if (response != null) {
                                 List<Story> list = response.body().stories;
                                 date = response.body().date;
                                 if (list != null) {
                                     mAdapter.refreshViewByReplaceData(list);
                                     mListView.renderViewByResult(false, list.size(), list.isEmpty());
                                 }
                             }
                             dismissProgress();
                         }

                         @Override
                         public void onFailure(Throwable t) {
                             AppUtil.showShortMessage(thisActivity, "首页数据获取失败" + t.getMessage());
                             dismissProgress();
                         }
                     }
        );
    }

    public void getNextData() {
        if (date != 0) {
            Call<Story.StoryResult> call = Net.getApi(StoryApi.class).beforeStories(date);
            call.enqueue(new Callback<Story.StoryResult>() {
                @Override
                public void onResponse(Response<Story.StoryResult> response, Retrofit retrofit) {
                    if(response.body() != null){
                        List<Story> list = response.body().stories;
                        mAdapter.refreshViewByAddData(list);
                        mListView.renderViewByResult(false, Constants.PAGE_LIMIT, list.isEmpty());
                        date--;
                    }
                    dismissProgress();
                }

                @Override
                public void onFailure(Throwable t) {
                    AppUtil.showShortMessage(thisActivity, "往期数据获取失败" + t.getMessage());
                    dismissProgress();
                }
            });
        }

    }

    public void dismissProgress() {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
