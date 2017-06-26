package com.a_liya.uimode.demo;

import android.app.Application;

import com.a_liya.uimode.UiModeManager;

/**
 * Application
 *
 * @author a_liYa
 * @date 2017/6/24 19:04.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UiModeManager.init(this, R.styleable.AppUiMode);

    }
}
