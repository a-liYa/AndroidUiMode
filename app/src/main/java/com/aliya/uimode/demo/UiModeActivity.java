package com.aliya.uimode.demo;

import android.os.Bundle;
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
                setTheme(R.style.AppTheme);

                break;
            case R.id.btn_right:
                setTheme(R.style.NightAppTheme);

                break;
        }
    }

}
