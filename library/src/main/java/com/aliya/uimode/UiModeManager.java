package com.aliya.uimode;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;

import com.aliya.uimode.apply.ApplyAlpha;
import com.aliya.uimode.apply.ApplyBackground;
import com.aliya.uimode.apply.ApplyDivider;
import com.aliya.uimode.apply.ApplyForeground;
import com.aliya.uimode.apply.ApplySrc;
import com.aliya.uimode.apply.ApplyTextColor;
import com.aliya.uimode.factory.UiModeInflaterFactory;
import com.aliya.uimode.intef.ApplyPolicy;
import com.aliya.uimode.intef.InflaterSupport;
import com.aliya.uimode.intef.UiApply;
import com.aliya.uimode.mode.UiMode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * UiMode管理类
 *
 * @author a_liYa
 * @date 2017/6/23 10:51.
 */
public class UiModeManager implements ApplyPolicy, InflaterSupport {

    private static volatile UiModeManager sInstance;

    private static Context sContext;

    private static Set<Integer> sSupportAttrIds;
    private static Map<String, UiApply> sSupportApplies = new HashMap<>();

    static {
        sSupportApplies.put("background", new ApplyBackground());
        sSupportApplies.put("foreground", new ApplyForeground());
        sSupportApplies.put("alpha", new ApplyAlpha());
        sSupportApplies.put("textColor", new ApplyTextColor());
        sSupportApplies.put("divider", new ApplyDivider());
        sSupportApplies.put("src", new ApplySrc());
    }

    private UiModeManager() {
    }

    public static UiModeManager get() {
        if (sInstance == null) {
            synchronized (UiModeManager.class) {
                if (sInstance == null) {
                    sInstance = new UiModeManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化： 持有ApplicationContext引用，保存支持的Attr
     *
     * @param context Context
     * @param attrs   支持夜间模式的属性数组
     */
    public static final void init(Context context, int[] attrs) {

        sContext = context.getApplicationContext();

        addSupportAttrIds(attrs);

        if (sContext instanceof Application) {

            ((Application) sContext).unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
            ((Application) sContext).registerActivityLifecycleCallbacks(lifecycleCallbacks);

            LayoutInflater inflater = LayoutInflater.from(sContext);
            if (LayoutInflaterCompat.getFactory(inflater) == null) {
                LayoutInflaterCompat.setFactory(inflater, obtainInflaterFactory());
            }
        }

    }

    private static final Application.ActivityLifecycleCallbacks lifecycleCallbacks =
            new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    long startMs = SystemClock.uptimeMillis();
                    Log.e("TAG", "---------------onActivityDestroyed " + startMs);
                    UiMode.removeUselessViews(activity);
                    long endMs = SystemClock.uptimeMillis();
                    Log.e("TAG", "---------------onActivityDestroyed 回收耗时 " + (endMs - startMs) + " >>> " + endMs);
                }
            };


    @Override
    public UiApply obtainApplyPolicy(String key) {
        return sSupportApplies.get(key);
    }

    @Override
    public boolean isSupportApply(String key) {
        return sSupportApplies.containsKey(key);
    }

    @Override
    public boolean isSupportAttrId(Integer attrId) {
        return sSupportAttrIds != null && sSupportAttrIds.contains(attrId);
    }

    public static void addSupportAttrIds(int[] attrs) {
        if (attrs == null) return;
        // 不存在，创建
        if (sSupportAttrIds == null) {
            synchronized (UiModeManager.class) {
                if (sSupportAttrIds == null)
                    // 创建时指定初始容量，更节省内存
                    sSupportAttrIds = new HashSet<>(attrs.length);
            }
        }
        // 添加全部
        for (int attrId : attrs) {
            sSupportAttrIds.add(attrId);
        }
    }

    public static LayoutInflaterFactory obtainInflaterFactory() {
        return UiModeInflaterFactory.get(get());
    }
}
