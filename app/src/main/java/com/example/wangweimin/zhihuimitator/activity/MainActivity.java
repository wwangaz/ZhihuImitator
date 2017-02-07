package com.example.wangweimin.zhihuimitator.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.adapter.DrawerMenuAdapter;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.Net;
import com.example.wangweimin.zhihuimitator.dataSource.retrofit.api.StoryApi;
import com.example.wangweimin.zhihuimitator.fragment.CollectFragment;
import com.example.wangweimin.zhihuimitator.fragment.StoryFragment;
import com.example.wangweimin.zhihuimitator.fragment.ThemeStoryFragment;
import com.example.wangweimin.zhihuimitator.model.Themes;
import com.example.wangweimin.zhihuimitator.view.RecyclerListView;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by wangweimin on 15/10/29.
 */
public class MainActivity extends BaseActivity {
    private final static String TAG = "MainActivity";

    @Bind(R.id.main_tool_bar)
    Toolbar mToolBar;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.drawer_menu_list)
    RecyclerListView mDrawerMenuList;

    private DrawerMenuAdapter mAdapter;

    private Fragment mCurrentFragment;

    private String currentThemeId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterViews(Bundle saveInstanceState) {
        setSupportActionBar(mToolBar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        setupDrawerContent();

        showFragmentByTag(StoryFragment.TAG, new StoryFragment());

        View.OnClickListener collectListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentByTag(CollectFragment.TAG, new CollectFragment());
                mDrawerLayout.closeDrawers();
            }
        };

        View.OnClickListener homeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                showFragmentByTag(StoryFragment.TAG, new StoryFragment());
            }
        };

        mAdapter = new DrawerMenuAdapter(collectListener, homeListener);
        mAdapter.setHasHeader(true);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseRecyclerListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                // 17/1/22 切换到theme story fragment
                // TODO: 17/2/7 切换actionbar
                String themeId = mAdapter.getData().get(position).id;
                showFragmentByTag(ThemeStoryFragment.TAG, ThemeStoryFragment.newInstance(themeId));
                if (!themeId.equals(currentThemeId) && mCurrentFragment instanceof ThemeStoryFragment) {
                    ((ThemeStoryFragment) mCurrentFragment).refreshDataById(themeId);
                }
                mDrawerLayout.closeDrawers();
            }
        });
        mDrawerMenuList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent() {

        Net.getApi(StoryApi.class).getThemes().enqueue(new Callback<Themes.ThemesResult>() {
            @Override
            public void onResponse(Response<Themes.ThemesResult> response, Retrofit retrofit) {
                if (response.body() != null)
                    mAdapter.refreshViewByReplaceData(response.body().others);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void showFragmentByTag(String tag, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment addedFragment = manager.findFragmentByTag(tag);
        if (addedFragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            if (mCurrentFragment != null) transaction.hide(mCurrentFragment);
            transaction.show(addedFragment).commit();
            mCurrentFragment = addedFragment;
        } else {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.content_frame, fragment, tag);
            if (mCurrentFragment != null) transaction.hide(mCurrentFragment);
            transaction.commit();
            mCurrentFragment = fragment;
        }
    }
}
