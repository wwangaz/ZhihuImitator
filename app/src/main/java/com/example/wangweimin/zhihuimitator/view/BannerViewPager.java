package com.example.wangweimin.zhihuimitator.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by wangweimin on 16/8/18.
 */

public class BannerViewPager extends ViewPager {

    private static final String TAG = "BannerViewPager";

    private static final int LOOPING_BANNER = 0;

    private static final int LOOPING_DELAY = 4000;

    private static final int LOOPING_DURATION = 500;

    private BannerHandler mHandler;

    private OnTouchEventListener touchEventListener;

    public interface OnTouchEventListener {
        void onToucheEvent(MotionEvent event);
    }

    public BannerViewPager(Context context) {
        super(context);
        init();
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        try {
            mHandler = new BannerHandler(this);
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(getContext(), new LinearInterpolator());
            field.set(this, fixedSpeedScroller);
            fixedSpeedScroller.setmDuration(LOOPING_DURATION);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }


    private static class BannerHandler extends Handler {

        private final WeakReference<BannerViewPager> bannerViewPagerWeakReference;

        public BannerHandler(BannerViewPager bannerViewPager) {
            this.bannerViewPagerWeakReference = new WeakReference<>(bannerViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BannerViewPager bannerViewPager = bannerViewPagerWeakReference.get();
            if (bannerViewPager != null) {
                if (msg.what == LOOPING_BANNER && bannerViewPager.getAdapter() != null) {
                    int currentItem = bannerViewPager.getCurrentItem();
                    bannerViewPager.setCurrentItem(currentItem + 1);
                    bannerViewPager.startLoopingBanner();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopLoopingBanner();
                break;
            case MotionEvent.ACTION_MOVE:
                stopLoopingBanner();
                break;
            case MotionEvent.ACTION_UP:
                startLoopingBanner();
                break;
            case MotionEvent.ACTION_CANCEL:
                stopLoopingBanner();
                break;
        }

        if (touchEventListener != null) {
            touchEventListener.onToucheEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void startLoopingBanner() {
        mHandler.sendEmptyMessageDelayed(LOOPING_BANNER, LOOPING_DELAY);
    }

    public void stopLoopingBanner() {
        mHandler.removeMessages(LOOPING_BANNER);
    }
}
