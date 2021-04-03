package com.aliya.uimode;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatDelegate;

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
     * @see androidx.appcompat.app.AppCompatDelegateImpl#updateForNightMode(int, boolean) 
     */
    public static boolean updateUiModeForApplication(Context context,
                                                     @AppCompatDelegate.NightMode int mode) {
        final Resources res = context.getApplicationContext().getResources();
        final Configuration conf = res.getConfiguration();
        final int currentNightMode = conf.uiMode & Configuration.UI_MODE_NIGHT_MASK;

        final int newNightMode;
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                newNightMode = Configuration.UI_MODE_NIGHT_YES;
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                newNightMode = Configuration.UI_MODE_NIGHT_NO;
                break;
            default:
                newNightMode = Resources.getSystem().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                break;
        }

        if (currentNightMode != newNightMode) {
            final Configuration config = new Configuration(conf);
            final DisplayMetrics metrics = res.getDisplayMetrics();

            // Update the UI Mode to reflect the new night mode
            config.uiMode = newNightMode | (config.uiMode & ~Configuration.UI_MODE_NIGHT_MASK);
            res.updateConfiguration(config, metrics);

            // We may need to flush the Resources' drawable cache due to framework bugs..
            ResourcesFlusher.flush(res);
        }

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            ResourcesFlusher.flush(sAppContext.getResources());
//        }
        return currentNightMode != newNightMode;
    }


}
