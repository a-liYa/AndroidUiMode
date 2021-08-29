package com.aliya.uimode.sample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.aliya.uimode.UiModeManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatDelegate;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

/**
 * UiMode 相关信息存储以及切换封装
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
        if (sharedPreferences.contains(KEY_UI_MODE)) {
            uiMode = sharedPreferences.getInt(KEY_UI_MODE, MODE_NIGHT_NO);
        } else {
            uiMode = AppCompatDelegate.getDefaultNightMode();
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

    public static void setUiMode(@ApplicableNightMode int uiMode) {
        if (_get().uiMode != uiMode) {
            _get().uiMode = uiMode;
            _get().sharedPreferences.edit().putInt(KEY_UI_MODE, uiMode).apply();
            UiModeManager.setUiMode(uiMode);
        }
    }

    public static void init(Context context) {
        sContext = context.getApplicationContext();
        UiModeManager.init(sContext, null, new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                // 此处可自定义拦截创建View
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return onCreateView(null, name, context, attrs);
            }
        });
        UiModeManager.setDefaultUiMode(_get().uiMode);
    }


    @ApplicableNightMode
    public static int getUiMode() {
        return _get().uiMode;
    }

    @IntDef({MODE_NIGHT_NO, MODE_NIGHT_YES, MODE_NIGHT_FOLLOW_SYSTEM})
    @Retention(RetentionPolicy.SOURCE)
    @interface ApplicableNightMode {}

}
