package com.aliya.uimode.demo;

import android.app.Activity;
import android.support.annotation.StyleRes;

import com.aliya.uimode.UiModeManager;

/**
 * 夜间模式 对应主题 配置
 *
 * @author a_liYa
 * @date 2017/6/29 09:28.
 */
public class ThemeMode {

    private boolean isNightMode;

    private int mDayTheme = NO_VALUE;
    private int mNightTheme = NO_VALUE;

    private static final int NO_VALUE = -1;
    private static final int DEFAULT_THEME = NO_VALUE;

    private static ThemeMode sInstance;

    private ThemeMode() {
    }

    public static void initTheme(@StyleRes int dayTheme, @StyleRes int nightTheme) {
        _get().setDayTheme(dayTheme).setNightTheme(nightTheme);
    }

    private static ThemeMode _get() {
        if (sInstance == null) {
            synchronized (ThemeMode.class) {
                if (sInstance == null) {
                    sInstance = new ThemeMode();
                }
            }
        }
        return sInstance;
    }

    public int getDayTheme() {
        return mDayTheme;
    }

    public ThemeMode setDayTheme(int dayTheme) {
        mDayTheme = dayTheme;
        return this;
    }

    public int getNightTheme() {
        return mNightTheme;
    }

    public ThemeMode setNightTheme(int nightTheme) {
        mNightTheme = nightTheme;
        return this;
    }

    private int getCurrentTheme() {
        return isNightMode ? mNightTheme : mDayTheme;
    }

    public static void setUiMode(boolean isNightMode) {
        if (_get().isNightMode != isNightMode) {
            _get().isNightMode = isNightMode;
            int theme = isNightMode ? _get().getNightTheme() : _get().getDayTheme();
            if (NO_VALUE != theme) {
                UiModeManager.setTheme(theme);
            }
        }
    }

    public static boolean isNightMode() {
        return _get().isNightMode;
    }

    public static void fitActivityTheme(Activity activity) {
        if (activity == null) return;

        int currTheme = _get().getCurrentTheme();
        if (NO_VALUE != currTheme && DEFAULT_THEME != currTheme) {
            activity.setTheme(currTheme);
        }
    }
}
