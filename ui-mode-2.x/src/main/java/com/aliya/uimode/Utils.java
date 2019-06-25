package com.aliya.uimode;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;

/**
 * 工具类
 *
 * @author a_liYa
 * @date 2018/1/26 16:56.
 */
final class Utils {

    public static int getManifestActivityTheme(Activity activity) {
        try {
            return activity.getPackageManager().getActivityInfo(new ComponentName(activity,
                    activity.getClass()), PackageManager.MATCH_DEFAULT_ONLY).theme;
        } catch (Exception e) {
            // no-op
        }
        return 0;
    }

    public static int getManifestApplicationTheme(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_SHARED_LIBRARY_FILES).theme;
        } catch (Exception e) {
            // no-op
        }
        return 0;
    }

    /**
     * update UiMode
     *
     * @param mode    ui mode type
     * @param context context
     * @return true : 刷新成功
     * @see android.support.v7.app.AppCompatDelegateImplV14#updateForNightMode(int)
     */
    public static boolean updateUiModeForApplication(Context context,
                                                     @AppCompatDelegate.NightMode int mode) {
        final Resources res = context.getApplicationContext().getResources();
        final Configuration conf = res.getConfiguration();
        final int currentUiMode = conf.uiMode & Configuration.UI_MODE_NIGHT_MASK;

        final int newUiMode = (mode == AppCompatDelegate.MODE_NIGHT_YES)
                ? Configuration.UI_MODE_NIGHT_YES
                : Configuration.UI_MODE_NIGHT_NO;

        if (currentUiMode != newUiMode) {
            final Configuration config = new Configuration(conf);
            final DisplayMetrics metrics = res.getDisplayMetrics();

            // Update the UI Mode to reflect the new night mode
            config.uiMode = newUiMode | (config.uiMode & ~Configuration.UI_MODE_NIGHT_MASK);
            res.updateConfiguration(config, metrics);

            // We may need to flush the Resources' drawable cache due to framework bugs..
            ResourcesFlusher.flush(res);
        }

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            ResourcesFlusher.flush(sAppContext.getResources());
//        }
        return currentUiMode != newUiMode;
    }


}
