package com.aliya.uimode.sample;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

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

        Log.e("TAG", "App - defaultNightMode: " + AppCompatDelegate.getDefaultNightMode());

        AppUiMode.init(App.this);
    }

    public static String uiModeToString(int uiMode) {
        final int configUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (configUiMode) {
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                return "NIGHT_AUTO";
            case Configuration.UI_MODE_NIGHT_YES:
                return "夜间";
            case Configuration.UI_MODE_NIGHT_NO:
                return "白间";
            default:
                return "未知 - " + configUiMode;
        }
    }
}
