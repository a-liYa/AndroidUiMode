package com.aliya.uimode.demo;

import android.app.Activity;
import android.support.annotation.StyleRes;

import com.aliya.uimode.UiModeManager;
import com.aliya.uimode.mode.ThemeStore;

import java.util.Set;

/**
 * 夜间模式 对应主题 配置
 *
 * @author a_liYa
 * @date 2017/6/29 09:28.
 */
public class ThemeMode {

    private int mKeyMode;

    private static ThemeMode sInstance;

    public static final int KEY_DAY = 0;
    public static final int KEY_NIGHT = 1;

    public static ThemeMode get() {
        if (sInstance == null) {
            synchronized (ThemeMode.class) {
                if (sInstance == null) {
                    sInstance = new ThemeMode();
                }
            }
        }
        return sInstance;
    }

    public ThemeMode putDayTheme(@StyleRes int resId) {
        ThemeStore.putTheme(KEY_DAY, resId);
        return this;
    }

    public ThemeMode putNightTheme(@StyleRes int resId) {
        ThemeStore.putTheme(KEY_NIGHT, resId);
        return this;
    }

    public void setUiMode(boolean isNightMode) {
        if (isNightMode) {
            if (mKeyMode != KEY_NIGHT) {
                mKeyMode = KEY_NIGHT;
                fitTheme();
            }
        } else {
            if (mKeyMode != KEY_DAY) {
                mKeyMode = KEY_DAY;
                fitTheme();
            }
        }
    }

    private void fitTheme() {
        UiModeManager.fitTheme(mKeyMode);
    }

    public void fitActivityTheme(Activity activity) {
        if (activity == null) return;

        Set<Integer> themeSet = ThemeStore.getTheme(mKeyMode);

        if (themeSet != null) {
            for (int resId : themeSet) {
                activity.setTheme(resId);
            }
        }
    }

}
