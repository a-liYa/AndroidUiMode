package com.aliya.uimode.simple;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;

import com.aliya.uimode.UiModeManager;

/**
 * AppUiMode
 *
 * @author a_liYa
 * @date 2018/1/26 16:47.
 */
public final class AppUiMode {

    private static Context sContext;
    private static volatile AppUiMode sInstance;
    private static final String KEY_UI_MODE = "ui_mode";

    private int uiMode;
    private SharedPreferences sharedPreferences;

    private AppUiMode() {
        sharedPreferences = sContext.getSharedPreferences("AppUiMode", Activity.MODE_PRIVATE);
        uiMode = sharedPreferences.getInt(KEY_UI_MODE, AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void setUiMode(@AppCompatDelegate.NightMode int uiMode) {
        if (this.uiMode != uiMode) {
            this.uiMode = uiMode;
            sharedPreferences.edit().putInt(KEY_UI_MODE, uiMode).apply();
        }
    }

    private static AppUiMode _get() {
        if (sInstance == null) {
            synchronized (AppUiMode.class) {
                if (sInstance == null) {
                    sInstance = new AppUiMode();
                }
            }
        }
        return sInstance;
    }

    public static void init(Context context) {
        sContext = context.getApplicationContext();
        UiModeManager.init(sContext, null);
        UiModeManager.setDefaultUiMode(_get().uiMode);
    }

    public static void setNight(boolean night) {
        _get().setUiMode(
                night ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        UiModeManager.setUiMode(_get().uiMode);
    }

    public static boolean isNight() {
        return _get().uiMode == AppCompatDelegate.MODE_NIGHT_YES;
    }

}
