package com.aliya.uimode.sample.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aliya.uimode.intef.UiModeChangeListener;
import com.aliya.uimode.sample.AppUiMode;
import com.aliya.uimode.sample.MainActivity;
import com.aliya.uimode.sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * top bar
 *
 * @author a_liYa
 * @date 2018/2/1 16:56.
 */
public class TopBar extends FrameLayout implements View.OnClickListener, UiModeChangeListener {

    TextView mTvTitle;
    TextView mBtnSwitch;

    public TopBar(@NonNull Context context) {
        this(context, null);
    }

    public TopBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TopBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_top_bar, this, true);

        init();
    }

    private void init() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnSwitch = (TextView) findViewById(R.id.btn_switch);
        Context context = getContext();
        if (context instanceof Activity) {
            try {
                PackageManager packageManager = context.getPackageManager();
                ComponentName component = new ComponentName(context, context.getClass());
                ActivityInfo info = packageManager.getActivityInfo(component, 0);
                mTvTitle.setText(info.loadLabel(packageManager));
            } catch (PackageManager.NameNotFoundException e) {
                // no-op
            }
        }
        mBtnSwitch.setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);

        bindModeView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
                switchUiMode();
                break;
            case R.id.btn_back:
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.btn_home:
                getContext().startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }

    private void switchUiMode() {
        int nextUiMode;
        switch (AppUiMode.getUiMode()) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                nextUiMode = AppCompatDelegate.MODE_NIGHT_YES;
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                nextUiMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                break;
            default:
                nextUiMode = AppCompatDelegate.MODE_NIGHT_NO;
                break;
        }
        AppUiMode.applyUiMode(nextUiMode);
    }

    private void bindModeView() {
        switch(AppUiMode.getUiMode()) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                mBtnSwitch.setText("白间");
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                mBtnSwitch.setText("黑暗");
                break;
            default:
                mBtnSwitch.setText("跟随系统");
                break;
        }
    }

    @Override
    public void onUiModeChange() {
        bindModeView();
    }
}
