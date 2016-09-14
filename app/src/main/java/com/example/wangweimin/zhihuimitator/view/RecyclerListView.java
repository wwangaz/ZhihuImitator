package com.example.wangweimin.zhihuimitator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.base.BaseRecyclerListAdapter;

/**
 * Created by wangweimin on 15/12/10.
 */
public class RecyclerListView extends RecyclerView {

    private static int PAGE_LIMIT_NONE = -1;

    private boolean loadBottomDataCompleted = true;

    private View mFailView, mEmptyView, mLoadingView;

    private OnRecyclerViewScrollBottomListener scrollBottomListener;

    private Drawable mDivider;

    private boolean mDecorate;

    public interface OnRecyclerViewScrollBottomListener {
        void requestNextPage();
    }

    private OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == SCROLL_STATE_IDLE && loadBottomDataCompleted
                    && getAdapter().getItemViewType(((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition()) == BaseRecyclerListAdapter.TYPE_FOOTER) {
                if (scrollBottomListener != null) {
                    loadBottomDataCompleted = false;
                    scrollBottomListener.requestNextPage();
                }
            }
        }
    };

    public RecyclerListView(Context context) {
        super(context);
        init(context);
    }

    public RecyclerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerListView, 0, 0);
        mDivider = getResources().getDrawable(a.getResourceId(R.styleable.RecyclerListView_recycler_divider, R.drawable.deep_divider));
        init(context);
        a.recycle();
    }

    public RecyclerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerListView, 0, 0);
        mDivider = getResources().getDrawable(a.getResourceId(R.styleable.RecyclerListView_recycler_divider, R.drawable.deep_divider));
        init(context);
        a.recycle();
    }

    private void init(Context context) {
        setItemAnimator(new DefaultItemAnimator());
        setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration;
        if (mDivider != null) {
            dividerItemDecoration = new DividerItemDecoration(mDivider, DividerItemDecoration.VERTICAL_LIST);
        } else {
            dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
        }
        if (mDecorate) addItemDecoration(dividerItemDecoration);
        setOnScrollListener(onScrollListener);
    }

    private void onLoadNextComplete() {
        this.loadBottomDataCompleted = true;
    }

    public void renderViewByResult(boolean showFailView) {
        renderViewByResult(showFailView, PAGE_LIMIT_NONE, false);
    }

    public void renderViewByResult(boolean showFailView, int pageLimit, boolean isCurrentListEmpty) {
        onLoadNextComplete();
        if (showFailView) {
            setVisibility(INVISIBLE);
            setFailedViewVisibility(VISIBLE);
            setEmptyViewVisibility(GONE);
            setLoadingViewVisibility(GONE);
        } else {
            BaseRecyclerListAdapter adapter = (BaseRecyclerListAdapter) getAdapter();
            int size = adapter.getData().size();
            setFailedViewVisibility(GONE);
            setLoadingViewVisibility(GONE);
            if (size == 0) {
                setVisibility(INVISIBLE);
                setEmptyViewVisibility(VISIBLE);
            } else {
                setVisibility(VISIBLE);
                setEmptyViewVisibility(GONE);
                adapter.setHasFooter(pageLimit != PAGE_LIMIT_NONE && !isCurrentListEmpty);
            }
        }
    }

    public void setOnRecyclerViewScrollBottomListener(OnRecyclerViewScrollBottomListener listener) {
        this.scrollBottomListener = listener;
    }

    public void setFailView(View failView) {
        mFailView = failView;
    }

    public void setmEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setLoadingView(View loadingView) {
        mLoadingView = loadingView;
    }

    public void setDecorate(boolean decorate) {
        mDecorate = decorate;
    }

    private void setFailedViewVisibility(int visibility) {
        if (mFailView != null) {
            mFailView.setVisibility(visibility);
        }
    }

    private void setEmptyViewVisibility(int visibility) {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(visibility);
        }
    }

    private void setLoadingViewVisibility(int visibility) {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(visibility);
        }
    }
}
