package com.example.wangweimin.zhihuimitator.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.model.Themes;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangweimin on 17/1/22.
 */

public class DrawerMenuAdapter extends BaseRecyclerListAdapter<Themes, DrawerMenuAdapter.DrawerMenuHolder> {
    public View.OnClickListener mCollectListener;
    public View.OnClickListener mHomeListener;

    public DrawerMenuAdapter(View.OnClickListener collectListener, View.OnClickListener homeListener) {
        mCollectListener = collectListener;
        mHomeListener = homeListener;
    }

    @Override
    protected DrawerMenuHolder onCreateItemViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_action_layout, viewGroup, false);
        return new DrawerMenuHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(DrawerMenuHolder viewHolder, int position) {
        Themes themes = getData().get(position);
        viewHolder.actionName.setText(themes.name);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nav_header, viewGroup, false);
        DrawerHeaderHolder mHeaderHolder = new DrawerHeaderHolder(view);
        mHeaderHolder.mCollectText.setOnClickListener(mCollectListener);
        mHeaderHolder.mHomeActionLayout.setOnClickListener(mHomeListener);
        return mHeaderHolder;
    }

    public class DrawerHeaderHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.menu_collect_text)
        TextView mCollectText;

        @Bind(R.id.menu_download_text)
        TextView mDownloadText;

        @Bind(R.id.home_action)
        LinearLayout mHomeActionLayout;

        public DrawerHeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DrawerMenuHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.action_name)
        TextView actionName;

        public DrawerMenuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
