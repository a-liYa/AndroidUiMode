package com.aliya.uimode.demo;

import android.app.Application;

import com.aliya.uimode.UiModeManager;
import com.squareup.leakcanary.LeakCanary;

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

        ThemeMode.initTheme(R.style.AppTheme, R.style.NightAppTheme);
        UiModeManager.init(this, R.styleable.SupportUiMode);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

    }

}
