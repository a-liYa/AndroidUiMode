package com.aliya.uimode;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;

import com.aliya.uimode.apply.ApplyAlpha;
import com.aliya.uimode.apply.ApplyBackground;
import com.aliya.uimode.apply.ApplyButton;
import com.aliya.uimode.apply.ApplyDivider;
import com.aliya.uimode.apply.ApplyDrawableBottom;
import com.aliya.uimode.apply.ApplyDrawableLeft;
import com.aliya.uimode.apply.ApplyDrawableRight;
import com.aliya.uimode.apply.ApplyDrawableTop;
import com.aliya.uimode.apply.ApplyForeground;
import com.aliya.uimode.apply.ApplyInvalidate;
import com.aliya.uimode.apply.ApplyNavIcon;
import com.aliya.uimode.apply.ApplyProgressDrawable;
import com.aliya.uimode.apply.ApplySrc;
import com.aliya.uimode.apply.ApplyTextColor;
import com.aliya.uimode.apply.ApplyTextColorHint;
import com.aliya.uimode.apply.ApplyTheme;
import com.aliya.uimode.apply.ApplyThumb;
import com.aliya.uimode.factory.UiModeInflaterFactory;
import com.aliya.uimode.intef.ApplyPolicy;
import com.aliya.uimode.intef.InflaterSupport;
import com.aliya.uimode.intef.UiApply;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.ThemeStore;
import com.aliya.uimode.mode.UiMode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * UiMode管理类
 *
 * @author a_liYa
 * @date 2017/6/23 10:51.
 */
public final class UiModeManager implements ApplyPolicy {

    private static final String TAG = "UiMode";

    private static Context sContext;

    // 指定支持的 R.attr 属性集合
    private static Set<Integer> sSupportAttrIds;
    private static Map<String, UiApply> sSupportApplies = new HashMap<>();

    private static volatile UiModeManager sInstance;

    static {
        sSupportApplies.put(Attr.NAME_THEME, new ApplyTheme());
        sSupportApplies.put(Attr.NAME_BG, new ApplyBackground());
        sSupportApplies.put(Attr.NAME_FG, new ApplyForeground());
        sSupportApplies.put(Attr.NAME_ALPHA, new ApplyAlpha());
        sSupportApplies.put(Attr.NAME_TC, new ApplyTextColor());
        sSupportApplies.put(Attr.NAME_TCH, new ApplyTextColorHint());
        sSupportApplies.put(Attr.NAME_DIVIDER, new ApplyDivider());
        sSupportApplies.put(Attr.NAME_SRC, new ApplySrc());
        sSupportApplies.put(Attr.NAME_NI, new ApplyNavIcon());
        sSupportApplies.put(Attr.NAME_BUTTON, new ApplyButton());
        sSupportApplies.put(Attr.NAME_PROGRESS_DRAWABLE, new ApplyProgressDrawable());
        sSupportApplies.put(Attr.NAME_THUMB, new ApplyThumb());
        sSupportApplies.put(Attr.NAME_DRAWABLE_TOP, new ApplyDrawableTop());
        sSupportApplies.put(Attr.NAME_DRAWABLE_BOTTOM, new ApplyDrawableBottom());
        sSupportApplies.put(Attr.NAME_DRAWABLE_RIGHT, new ApplyDrawableRight());
        sSupportApplies.put(Attr.NAME_DRAWABLE_LEFT, new ApplyDrawableLeft());

        sSupportApplies.put(Attr.INVALIDATE, new ApplyInvalidate());
    }

    private InflaterSupport mInflaterSupport;

    private UiModeManager() {
        mInflaterSupport = new InflaterSupport() {
            @Override
            public boolean isSupportApply(String key) {
                return sSupportApplies.containsKey(key);
            }

            @Override
            public boolean isSupportAttrId(Integer attrId) {
                return sSupportAttrIds != null && sSupportAttrIds.contains(attrId);
            }
        };
    }

    @Override
    public UiApply obtainApplyPolicy(String key) {
        return sSupportApplies.get(key);
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

    private static final Application.ActivityLifecycleCallbacks lifecycleCallbacks =
            new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    AppStack.pushActivity(activity);
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
                    AppStack.removeActivity(activity);
                    UiMode.removeUselessViews(activity);
                }
            };

    /**
     * 初始化： 持有ApplicationContext引用，保存支持的Attr
     *
     * @param context Context
     * @param attrs   支持夜间模式的属性数组
     */
    public static final void init(Context context, int[] attrs) {

        sContext = context.getApplicationContext();
        Log.init(sContext);

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

    /**
     * 切换主题
     *
     * @param resId a theme style res id
     */
    public static void setTheme(int resId) {
        if (sContext == null) {
            Log.e(TAG, "Using the ui mode, you need to initialize");
            return;
        }

        // 设置所有Activity主题
        Stack<Activity> appStack = AppStack.getAppStack();
        if (appStack != null) {
            Iterator<Activity> iterator = appStack.iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();
                if (next != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (next.isDestroyed()) continue;
                    }
                    next.setTheme(resId);
                }
            }
        }

        // 设置Application的主题
        sContext.setTheme(resId);

        // 执行View到对应主题
        UiMode.applyUiMode(get());

    }

    /**
     * 适配主题组，适用于模块化开发
     *
     * @param keyMode key mode
     */
    public static void fitTheme(int keyMode) {
        if (sContext == null) {
            Log.e(TAG, "Using the ui mode, you need to initialize");
            return;
        }

        Set<Integer> themeSet = ThemeStore.getTheme(keyMode);

        if (themeSet == null) return;

        // 设置所有Activity主题
        Stack<Activity> appStack = AppStack.getAppStack();
        if (appStack != null) {
            Iterator<Activity> iterator = appStack.iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();
                if (next != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (next.isDestroyed()) continue;
                    }
                    for (int resId : themeSet) {
                        next.setTheme(resId);
                    }
                }
            }
        }

        // 设置Application的主题
        for (int resId : themeSet) {
            sContext.setTheme(resId);
        }

        // 执行View到对应主题
        UiMode.applyUiMode(get());
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

    public static void setInflaterFactor(LayoutInflater inflater) {
        if (sContext != null) {
            LayoutInflaterCompat.setFactory(inflater, UiModeManager.obtainInflaterFactory());
        } else {
            Log.e(TAG, "Using the ui mode, you need to initialize");
        }
    }

    public static LayoutInflaterFactory obtainInflaterFactory() {
        return UiModeInflaterFactory.get(get().mInflaterSupport);
    }

}
