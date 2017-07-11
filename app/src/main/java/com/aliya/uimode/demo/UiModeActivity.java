package com.aliya.uimode.demo;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.aliya.uimode.demo.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 夜间模式切换 - 页面
 *
 * @author a_liYa
 * @date 2017/6/27 下午1:00.
 */
public class UiModeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_mode);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                long start = SystemClock.uptimeMillis();
                ThemeMode.setUiMode(false);
                Log.e("TAG", "模式切换执行的时间 " + (SystemClock.uptimeMillis() - start) + " ms");
                break;
            case R.id.btn_right:
                long startMs = SystemClock.uptimeMillis();
                ThemeMode.setUiMode(true);
                Log.e("TAG", "模式切换执行的时间 " + (SystemClock.uptimeMillis() - startMs) + " ms");
                break;
        }
    }

}
