package com.aliya.uimode.demo;

import android.app.Application;

import com.aliya.uimode.UiModeManager;

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

        ThemeMode.init(this);
        ThemeMode.initTheme(R.style.AppTheme, R.style.NightAppTheme);
        UiModeManager.init(this, R.styleable.SupportUiMode);

    }

}
