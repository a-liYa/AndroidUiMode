package com.aliya.uimode.simple.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aliya.uimode.simple.MainActivity;
import com.aliya.uimode.simple.R;
import com.aliya.uimode.simple.UiMode;

/**
 * top bar
 *
 * @author a_liYa
 * @date 2018/2/1 16:56.
 */
public class TopBar extends FrameLayout implements View.OnClickListener {

    TextView mTvTitle;

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
        findViewById(R.id.btn_switch).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
                UiMode.setNight(!UiMode.isNight());
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
}
