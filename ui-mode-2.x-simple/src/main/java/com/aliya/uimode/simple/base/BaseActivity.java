package com.aliya.uimode.simple.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aliya.uimode.UiModeManager;
import com.aliya.uimode.intef.UiModeChangeListener;

/**
 * Base Activity
 *
 * @author a_liYa
 * @date 2018/2/1 15:05.
 */
public class BaseActivity extends AppCompatActivity implements UiModeChangeListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        UiModeManager.setInflaterFactor(getLayoutInflater());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onUiModeChange() {
        Log.e("TAG","onUiModeChange " + this);
    }

}
