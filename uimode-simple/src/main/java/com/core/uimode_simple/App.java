package com.core.uimode_simple;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

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
        // 默认设置为日夜间模式
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}
