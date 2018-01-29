package com.core.uimode_simple;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.aliya.uimode.UiModeManager;

/**
 * Application
 *
 * @author a_liYa
 * @date 2018/1/23 10:05.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UiModeManager.init(this, null);
        UiModeManager.setDefaultUiMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}
