package com.aliya.uimode.sample.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.aliya.uimode.UiModeManager;
import com.aliya.uimode.intef.UiModeChangeListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        UiModeManager.applyUiModeViews(this);
        Log.e("TAG", "onConfigurationChanged: " + getResources().getConfiguration().uiMode);
    }

    @Override
    public void onUiModeChange() {
        Log.e("TAG","onUiModeChange " + getResources().getConfiguration().uiMode);
    }

}
