package com.example.wangweimin.zhihuimitator.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.fragment.CollectFragment;
import com.example.wangweimin.zhihuimitator.fragment.StoryFragment;

import butterknife.Bind;

/**
 * Created by wangweimin on 15/10/29.
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.main_tool_bar)
    Toolbar mToolBar;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.menu_collect_text)
    TextView mCollectText;

    @Bind(R.id.menu_download_text)
    TextView mDownloadText;

    private CollectFragment mCollectFragment;
    private StoryFragment mStoryFragment;
    private FragmentManager manager = getSupportFragmentManager();

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

        setupDrawerContent(mNavigationView);

        mCollectFragment = new CollectFragment();
        mStoryFragment = new StoryFragment();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content_frame, mCollectFragment, CollectFragment.TAG);
        transaction.add(R.id.content_frame, mStoryFragment, StoryFragment.TAG);
        transaction.commit();

        mCollectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().show(mCollectFragment).hide(mStoryFragment).commit();
                mDrawerLayout.closeDrawers();
            }
        });
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


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main:
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.show(mStoryFragment).hide(mCollectFragment).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }
}
