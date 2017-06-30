package com.aliya.uimode.demo;

import android.app.Activity;

import com.aliya.uimode.UiModeManager;

/**
 * 夜间模式 对应主题 配置
 *
 * @author a_liYa
 * @date 2017/6/29 09:28.
 */
public class ThemeMode {

    private static boolean isNightMode = false;

    private static final int DAY_THEME = R.style.AppTheme;
    private static final int NIGHT_THEME = R.style.NightAppTheme;

    private static final int DEFAULT_THEME = DAY_THEME;

    public static final void setUiMode(boolean isNightMode) {
        if (ThemeMode.isNightMode != isNightMode) {
            ThemeMode.isNightMode = isNightMode;
            UiModeManager.setTheme(getCurrentTheme());
        }
    }

    public static final int getCurrentTheme() {
        return isNightMode ? NIGHT_THEME : DAY_THEME;
    }

    public static final void setTheme2Activity(Activity activity) {
        if (activity == null) return;
        if (DEFAULT_THEME != getCurrentTheme()) {
            activity.setTheme(getCurrentTheme());
        }

    }
}
