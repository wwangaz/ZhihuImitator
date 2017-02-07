package com.example.wangweimin.zhihuimitator.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.wangweimin.zhihuimitator.Constants;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.activity.StoryDetailActivity;
import com.example.wangweimin.zhihuimitator.adapter.ThemeStoryAdapter;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.base.BaseFragment;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.Net;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.api.StoryApi;
import com.example.wangweimin.zhihuimitator.model.Story;
import com.example.wangweimin.zhihuimitator.model.ThemesDetail;
import com.example.wangweimin.zhihuimitator.view.RecyclerListView;

import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 17/1/22.
 */

public class ThemeStoryFragment extends BaseFragment {

    public final static String TAG = "ThemeStoryFragment";

    public final static String THEME_ID = "theme_id";

    @Bind(R.id.theme_recycler_view)
    RecyclerListView mRecyclerView;

    private ThemeStoryAdapter mAdapter;
    private String themeId;
    private String lastStoryId;

    public static ThemeStoryFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(THEME_ID, id);
        ThemeStoryFragment fragment = new ThemeStoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void refreshDataById(String id){
        themeId = id;
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_story;
    }

    @Override
    protected void afterViews(Bundle savedInstanceState) {

        themeId = getArguments().getString(THEME_ID);

        mAdapter = new ThemeStoryAdapter(getActivity());
        mAdapter.setHasHeader(true);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnRecyclerViewScrollBottomListener(new RecyclerListView.OnRecyclerViewScrollBottomListener() {
            @Override
            public void requestNextPage() {
                getNextPage();
            }
        });

        mAdapter.setOnRecyclerViewItemClickListener(new BaseRecyclerListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Story story = mAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.STORY_ID, story.id);
                ((BaseActivity)getActivity()).pushView(StoryDetailActivity.class, bundle);
            }
        });

        getData();
    }

    private void getData() {
        Net.getApi(StoryApi.class).getThemesDetail(themeId).enqueue(new Callback<ThemesDetail>() {
            @Override
            public void onResponse(Response<ThemesDetail> response, Retrofit retrofit) {
                ThemesDetail detail = response.body();
                if (detail.stories != null) {
                    lastStoryId = detail.stories.get(detail.stories.size() - 1).id;
                    mAdapter.refreshViewByReplaceData(detail.stories);
                    mAdapter.setHeadData(detail.image, detail.description, detail.editors);
                    mRecyclerView.renderViewByResult(false, detail.stories.size(), detail.stories.isEmpty());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void getNextPage() {
        Net.getApi(StoryApi.class).getBeforeTheme(themeId, lastStoryId).enqueue(new Callback<Story.StoryResult>() {
            @Override
            public void onResponse(Response<Story.StoryResult> response, Retrofit retrofit) {
                List<Story> stories = response.body().stories;
                lastStoryId = stories.get(stories.size()-1).id;
                mAdapter.refreshViewByAddData(stories);
                mRecyclerView.renderViewByResult(false, 20, stories.isEmpty());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
