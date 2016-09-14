package com.example.wangweimin.zhihuimitator.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.example.wangweimin.zhihuimitator.MyApplication;
import com.example.wangweimin.zhihuimitator.R;
import com.example.wangweimin.zhihuimitator.activity.MainActivity;
import com.example.wangweimin.zhihuimitator.util.AppUtil;

import butterknife.ButterKnife;

/**
 * Created by wangweimin on 15/10/29.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected MyApplication mApp;
    protected Context mContext;
    protected Intent mIntent;
    protected LayoutInflater mLayoutInflater;
    protected BaseActivity thisActivity;

    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        thisActivity = this;
        mApp = (MyApplication) getApplication();
        mLayoutInflater = LayoutInflater.from(mContext);
        mIntent = getIntent();

        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
            afterViews(savedInstanceState);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void afterViews(Bundle saveInstanceState);

    public void pushView(Class<? extends Activity> activityClass, Bundle bundle){
        pushView(activityClass, bundle, true);
    }

    public void pushView(Class<? extends Activity> activityClass, Bundle bundle, boolean isAnimator) {
        Intent intent = new Intent(thisActivity, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isAnimator) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.activity_close_enter);
        }
    }

    public void popView() {
        thisActivity.finish();
        overridePendingTransition(R.anim.activity_close_enter, R.anim.slide_out_right);
    }

    protected void backToMainActivity() {
        Intent intent = new Intent(thisActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        popView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            popLastActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void popLastActivity() {
        if (thisActivity instanceof MainActivity) {
            if ((System.currentTimeMillis() - exitTime) > 2000){
                AppUtil.showShortMessage(mContext, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            }else{
                thisActivity.finish();
            }
        }else{
            popView();
        }
    }

    public void showBackDialog(){
        new AlertDialog.Builder(this).setCancelable(false).setMessage("是否确认退出？")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popView();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}
