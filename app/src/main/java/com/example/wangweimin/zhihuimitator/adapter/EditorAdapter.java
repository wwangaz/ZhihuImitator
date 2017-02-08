package com.example.wangweimin.zhihuimitator.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;
import com.example.wangweimin.zhihuimitator.model.Editor;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangweimin on 17/2/8.
 */

public class EditorAdapter extends BaseRecyclerListAdapter<Editor, EditorAdapter.EditorViewHolder> {
    private Activity mActivity;

    public EditorAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected EditorViewHolder onCreateItemViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.editor_item, viewGroup, false);
        return new EditorViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(final EditorViewHolder viewHolder, int position) {
        Editor editor = getData().get(position);
        Glide.with(mActivity).load(editor.avatar).asBitmap().into(new BitmapImageViewTarget(viewHolder.avatar) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(mActivity.getResources(), resource);
                viewHolder.avatar.setImageDrawable(drawable);
            }
        });
        viewHolder.name.setText(editor.name);
        viewHolder.title.setText(editor.bio);
    }

    class EditorViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.avatar)
        ImageView avatar;

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.title)
        TextView title;

        public EditorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
