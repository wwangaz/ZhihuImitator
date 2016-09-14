package com.example.wangweimin.zhihuimitator.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangweimin.zhihuimitator.R;

import butterknife.ButterKnife;

/**
 * Created by wangweimin on 16/8/22.
 */

public abstract class BaseFragment extends Fragment {
    protected String TAG;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            View view = inflater.inflate(layoutId, container, false);
            ButterKnife.bind(this, view);
            afterViews(savedInstanceState);
            return view;
        } else
            return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void afterViews(Bundle savedInstanceState);

    public void pushViews(Class<? extends Activity> activityClass, Bundle bundle, boolean isAnimator){
        Intent intent = new Intent(getActivity(), activityClass);
        if(bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        if (isAnimator) getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.activity_close_enter);
    }

    public void pushViews(Class<? extends Activity> activityClass, Bundle bundle){
        pushViews(activityClass, bundle, false);
    }

    protected void pushForResultView(Class< ? extends Activity> activityClass, Bundle bundle, int requestCode){
        Intent intent = new Intent(getActivity(), activityClass);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.activity_close_enter);
    }
}
