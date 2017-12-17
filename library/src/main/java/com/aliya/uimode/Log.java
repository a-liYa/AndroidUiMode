package com.aliya.uimode;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Log日志类
 *
 * @author a_liYa
 * @date 2016-3-30 下午11:44:55
 */
final class Log {

    private static boolean isDebug = true;
    private static boolean debuggable = true;

    private static String TAG = "UiMode";

    private Log() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 初始化，无初始化默认日志开启
     *
     * @param context .
     */
    public static void init(Context context) {
        if (context == null) return;
        try {
            Log.debuggable = (context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES)
                    .applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            Log.debuggable = false;
        }
    }

    public static void setIsDebug(boolean isDebug) {
        Log.isDebug = isDebug;
    }

    public static boolean isDebuggable() {
        return debuggable;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug && debuggable)
            android.util.Log.i(TAG, msg + "");
    }

    public static void d(String msg) {
        if (isDebug && debuggable)
            android.util.Log.d(TAG, msg + "");
    }

    public static void e(String msg) {
        if (isDebug && debuggable)
            android.util.Log.e(TAG, msg + "");
    }

    public static void v(String msg) {
        if (isDebug && debuggable)
            android.util.Log.v(TAG, msg + "");
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug && debuggable)
            android.util.Log.i(tag, msg + "");
    }

    public static void d(String tag, String msg) {
        if (isDebug && debuggable)
            android.util.Log.d(tag, msg + "");
    }

    public static void e(String tag, String msg) {
        if (isDebug && debuggable)
            android.util.Log.e(tag, msg + "");
    }

    public static void v(String tag, String msg) {
        if (isDebug && debuggable)
            android.util.Log.v(tag, msg + "");
    }

}
