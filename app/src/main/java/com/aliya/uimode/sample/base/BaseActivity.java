package com.aliya.uimode.sample.base;

import android.content.res.Configuration;
import android.os.Bundle;

import com.aliya.uimode.UiModeManager;
import com.aliya.uimode.intef.UiModeChangeListener;
import com.aliya.uimode.sample.AppUiMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * Base Activity
 *
 * @author a_liYa
 * @date 2018/2/1 15:05.
 */
public class BaseActivity extends AppCompatActivity implements UiModeChangeListener {

    private int nightMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        UiModeManager.setInflaterFactor(getLayoutInflater());
        super.onCreate(savedInstanceState);
        nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final int newNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (AppUiMode.getUiMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM && nightMode != newNightMode) {
            UiModeManager.applyUiModeViews(this);
            nightMode = newNightMode;
        }
    }

    @Override
    public void onUiModeChange() {
        //
    }

}
