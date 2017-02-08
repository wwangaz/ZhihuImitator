package com.example.wangweimin.zhihuimitator.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.adapter.EditorAdapter;
import com.example.wangweimin.zhihuimitator.base.BaseActivity;
import com.example.wangweimin.zhihuimitator.model.Editor;
import com.example.wangweimin.zhihuimitator.view.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wangweimin on 17/2/8.
 */

public class EditorListActivity extends BaseActivity {

    public final static String TITLE = "title";
    public final static String EDITORS = "editors";

    @Bind(R.id.editor_recyclerView)
    RecyclerListView recyclerListView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private EditorAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor_list;
    }

    @Override
    protected void afterViews(Bundle saveInstanceState) {
        String title = getIntent().getStringExtra(TITLE);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }

        mAdapter = new EditorAdapter(thisActivity);
        recyclerListView.setAdapter(mAdapter);

        List<Editor> editors = (ArrayList<Editor>) getIntent().getSerializableExtra(EDITORS);
        if (editors != null) {
            mAdapter.refreshViewByReplaceData(editors);
        }
        // TODO: 17/2/8 增加推荐者详情
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                popView();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
