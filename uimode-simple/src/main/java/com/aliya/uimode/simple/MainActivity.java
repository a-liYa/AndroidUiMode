package com.aliya.uimode.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.aliya.uimode.UiModeManager;
import com.aliya.uimode.simple.base.BaseActivity;

/**
 * 主界面
 *
 * @author a_liYa
 * @date 2018/2/1 下午3:06.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_switch).setOnClickListener(this);

    }

    static boolean night = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:

                UiModeManager.setUiMode((night = !night)
                        ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

                break;
        }
    }

}
