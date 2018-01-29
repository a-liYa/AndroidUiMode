package com.aliya.uimode;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;

/**
 * 工具类
 *
 * @author a_liYa
 * @date 2018/1/26 16:56.
 */
final class Utils {

    public static int getManifestActivityTheme(Activity activity) {
        try {
            return activity.getPackageManager().getActivityInfo(
                    new ComponentName(activity, activity.getClass()), 0).theme;
        } catch (PackageManager.NameNotFoundException e) {
            // no-op
        }
        return 0;
    }

    public static int getManifestApplicationTheme(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), 0).theme;
        } catch (PackageManager.NameNotFoundException e) {
            // no-op
        }
        return 0;
    }

    public static boolean hasUiModeChange(int mode, Context context) {
        final Configuration config = context.getResources().getConfiguration();
        final int currentUiMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        final int newUiMode = (mode == AppCompatDelegate.MODE_NIGHT_YES)
                ? Configuration.UI_MODE_NIGHT_YES
                : Configuration.UI_MODE_NIGHT_NO;
        return currentUiMode != newUiMode;
    }

}
