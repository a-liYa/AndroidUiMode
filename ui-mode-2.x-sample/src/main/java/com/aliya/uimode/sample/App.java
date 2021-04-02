package com.aliya.uimode.sample;

import android.app.Application;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Application
 *
 * @author a_liYa
 * @date 2018/1/23 10:05.
 */
public class App extends Application {

    private final String PATH_VIVO_UI_MODE = "vivo_nightmode_used";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("TAG", "App - defaultNightMode: " + AppCompatDelegate.getDefaultNightMode());

        AppUiMode.init(App.this);

        registerUiModeObserver();
    }

    private void registerUiModeObserver() {
        getContentResolver().registerContentObserver(
                Settings.System.getUriFor(PATH_VIVO_UI_MODE), false,
                new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange) {
                        int uiMode = Settings.System.getInt(getContentResolver(), PATH_VIVO_UI_MODE, -2);
                        // uiMode = 1 时代表深色模式开启
                        Log.e("TAG", "onChange: " + selfChange + " - " + uiMode);
                    }
                });
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
