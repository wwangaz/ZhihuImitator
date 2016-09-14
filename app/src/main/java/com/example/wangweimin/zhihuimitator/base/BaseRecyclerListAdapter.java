package com.example.wangweimin.zhihuimitator.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangweimin.zhihuimitator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangweimin on 15/12/10.
 */
public abstract class BaseRecyclerListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    public static final int TYPE_HEADER = 1;

    public static final int TYPE_ITEM = 2;

    public static final int TYPE_FOOTER = 3;

    private static final int DEFAULT_FOOTER_LAYOUT = R.layout.loading_cell;

    protected List<T> mDataList;

    private boolean hasHeader = false;

    private boolean hasFooter = false;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClickListener(View view, int position);
    }

    private OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClickListener(View view, int position);
    }

    protected abstract VH onCreateItemViewHolder(ViewGroup viewGroup);

    protected abstract void onBindItemViewHolder(VH viewHolder, int position);

    public BaseRecyclerListAdapter() {
        this.mDataList = new ArrayList<>();
    }

    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
//        throw new IllegalAccessException("Please Override this Method if you want to add a「Header」to RecyclerView, or " +
//                "you should not call setHasHeader(true)");
        return null;
    }

    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(DEFAULT_FOOTER_LAYOUT, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader && position == 0) {
            return TYPE_HEADER;
        } else if (hasFooter && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent);
        } else if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent);
        } else {
            return onCreateItemViewHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            final int realPosition = position - getHeaderCount();
            onBindItemViewHolder((VH) holder, realPosition);
            if (onRecyclerViewItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRecyclerViewItemClickListener.onItemClickListener(holder.itemView, realPosition);
                    }
                });
            }
            if (onRecyclerViewItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onRecyclerViewItemLongClickListener.onItemLongClickListener(holder.itemView, realPosition);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size() + getHeaderCount() + getFooterCount();
    }

    public void refreshViewByAddData(List<T> list) {
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void refreshViewByReplaceData(List<T> list) {
        this.mDataList = list;
        notifyDataSetChanged();
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
        if (!hasFooter) {
            notifyItemChanged(getCount());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.onRecyclerViewItemLongClickListener = listener;
    }

    private int getHeaderCount() {
        return hasHeader ? 1 : 0;
    }

    private int getFooterCount() {
        return hasFooter ? 1 : 0;
    }

    public List<T> getData() {
        return mDataList;
    }

    public int getCount() {
        return mDataList == null ? 0: mDataList.size();
    }
}
