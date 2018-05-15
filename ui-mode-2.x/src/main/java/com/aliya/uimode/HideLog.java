package com.aliya.uimode;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * 内部封装的Log日志类，内部使用，禁止外部使用
 *
 * @author a_liYa
 * @date 2016-3-30 下午11:44:55
 * @hide
 */
final class HideLog {

    /**
     * 手动控制是否Debug，通过{@link #setIsDebug(boolean)}设置
     */
    private static boolean isDebug = true;
    /**
     * @see ./build.gradle 属性android.buildTypes.release/debug#debuggable true/false 来决定
     */
    private static boolean debuggable = true;

    private static final String TAG = "UiMode";

    private HideLog() {
    }

    /**
     * 初始化，无初始化默认日志开启
     *
     * @param context .
     */
    public static void init(Context context) {
        if (context == null) return;
        try {
            HideLog.debuggable =
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            HideLog.debuggable = false;
        }
    }

    public static void setIsDebug(boolean isDebug) {
        HideLog.isDebug = isDebug;
    }

    public static boolean isDebuggable() {
        return debuggable;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug && debuggable)
            Log.i(TAG, msg + "");
    }

    public static void d(String msg) {
        if (isDebug && debuggable)
            Log.d(TAG, msg + "");
    }

    public static void e(String msg) {
        if (isDebug && debuggable)
            Log.e(TAG, msg + "");
    }

    public static void v(String msg) {
        if (isDebug && debuggable)
            Log.v(TAG, msg + "");
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug && debuggable)
            Log.i(tag, msg + "");
    }

    public static void d(String tag, String msg) {
        if (isDebug && debuggable)
            Log.d(tag, msg + "");
    }

    public static void e(String tag, String msg) {
        if (isDebug && debuggable)
            Log.e(tag, msg + "");
    }

    public static void v(String tag, String msg) {
        if (isDebug && debuggable)
            Log.v(tag, msg + "");
    }

}
