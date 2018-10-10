package com.aliya.uimode;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
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
import com.aliya.uimode.intef.UiModeChangeListener;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.UiMode;

import java.util.HashMap;
import java.util.HashSet;
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
            public boolean isSupportApply(String name) {
                return sSupportApplies.containsKey(name);
            }

            @Override
            public boolean isSupportApplyType(String name, String type) {
                UiApply uiApply = sSupportApplies.get(name);
                return uiApply != null && uiApply.isSupportType(type);
            }

            @Override
            public boolean isSupportAttrId(Integer attrId) { // sSupportAttrIds == null 支持所有
                return sSupportAttrIds == null || sSupportAttrIds.contains(attrId);
            }
        };
    }

    /**
     * 获取UiApply的实现类
     *
     * @param key {@link Attr} 属性名称
     * @return UiApply，返回null时，说明不支持该属性
     */
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
     * @param attrs   支持UiMode的属性数组，为null时表示支持所有的属性
     */
    public static void init(Context context, int[] attrs) {

        sContext = context.getApplicationContext();
        HideLog.init(sContext);

        final int appTheme = Utils.getManifestApplicationTheme(sContext);
        if (appTheme != 0) {
            sContext.getTheme().applyStyle(appTheme, true);
        }

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

    public static void setDefaultUiMode(@AppCompatDelegate.NightMode int mode) {
        AppCompatDelegate.setDefaultNightMode(mode); // 设置默认的日夜间模式
    }


    public static void setUiMode(@AppCompatDelegate.NightMode int mode) {
        if (sContext == null) {
            HideLog.e(TAG, "Using the ui mode, you need to initialize");
            return;
        }

        final boolean uiModeChange = Utils.hasUiModeChange(mode, sContext);
        setDefaultUiMode(mode);
        int appTheme = 0;
        // 应用Application
        if (uiModeChange) {
            appTheme = Utils.getManifestApplicationTheme(sContext);
            if (appTheme != 0) {
                sContext.getTheme().applyStyle(appTheme, true);
            }
        }

        // 遍历应用所有Activity
        Stack<Activity> appStack = AppStack.getAppStack();
        if (appStack != null) {
            for (Activity next : appStack) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (next.isDestroyed()) continue;
                }

                if (next instanceof AppCompatActivity) {
                    ((AppCompatActivity) next).getDelegate().setLocalNightMode(mode);
                }

                if (uiModeChange) {
                    final int theme = Utils.getManifestActivityTheme(next);
                    if (theme != 0) {
                        next.getTheme().applyStyle(theme, true);
                    } else if (appTheme != 0) {
                        next.getTheme().applyStyle(appTheme, true);
                    }
                }

                if (next instanceof UiModeChangeListener) {
                    ((UiModeChangeListener) next).onUiModeChange();
                }

            }
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Utils.flushResourcesV16(sContext.getResources());
            }
        }

        // 应用UiMode
        UiMode.applyUiMode(get());
    }

    /**
     * 适配切换的主题
     *
     * @param resId a theme style res id
     * @see #setUiMode(int)
     * @deprecated 为兼容v1.x版本保留的方法
     */
    public static void setTheme(int resId) {
        if (sContext == null) {
            HideLog.e(TAG, "Using the ui mode, you need to initialize");
            return;
        }

        // 设置所有Activity主题
        Stack<Activity> appStack = AppStack.getAppStack();
        if (appStack != null) {
            for (Activity next : appStack) {
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

    public static void addSupportAttrIds(int[] attrs) {
        if (attrs == null) {
            sSupportAttrIds = null;
            return;
        }
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

    /**
     * 添加扩展 UiApply
     *
     * @param key   属性名称 {@link Attr}
     * @param apply 执行属性方法的实现类
     */
    public static void addSupportUiApply(String key, UiApply apply) {
        if (TextUtils.isEmpty(key) || apply == null) {
            HideLog.e(TAG, "UiApply or key can not be null");
            return;
        }
        sSupportApplies.put(key, apply);
    }

    public static void setInflaterFactor(LayoutInflater inflater) {
        if (sContext != null) {
            LayoutInflaterCompat.setFactory(inflater, UiModeManager.obtainInflaterFactory());
        } else {
            HideLog.e(TAG, "Using the ui mode, you need to initialize");
        }
    }

    /**
     * @return LayoutInflaterFactory
     * @see #setInflaterFactor(LayoutInflater)
     */
    public static LayoutInflaterFactory obtainInflaterFactory() {
        return UiModeInflaterFactory.get(get().mInflaterSupport);
    }

    /**
     * 设置日志 debug模式状态
     *
     * @param isDebug : false 强制关闭日志
     */
    public static void setLogDebug(boolean isDebug) {
        HideLog.setIsDebug(isDebug);
    }

}
