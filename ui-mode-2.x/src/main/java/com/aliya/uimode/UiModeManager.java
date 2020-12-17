package com.aliya.uimode;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
import com.aliya.uimode.intef.UiApply;
import com.aliya.uimode.intef.UiModeChangeListener;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.UiMode;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

/**
 * UiMode管理类
 *
 * @author a_liYa
 * @date 2017/6/23 10:51.
 */
public final class UiModeManager implements ApplyPolicy {

    private static final String TAG = "UiMode";

    private static Context sAppContext;
    private static LayoutInflater.Factory2 sFactory2;

    // 指定支持的 R.attr 属性集合
    static Set<Integer> sSupportAttrIds;
    final static Map<String, UiApply> sSupportApplies = new HashMap<>();

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

    private UiModeManager() {
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
    public static void init(Context context, int[] attrs, LayoutInflater.Factory2 factory2) {

        sAppContext = context.getApplicationContext();
        HideLog.init(sAppContext);
        sFactory2 = factory2;

        final int appTheme = Utils.getManifestApplicationTheme(sAppContext);
        if (appTheme != 0) {
            sAppContext.getTheme().applyStyle(appTheme, true);
        }

        addSupportAttrIds(attrs);

        if (sAppContext instanceof Application) {
            ((Application) sAppContext).unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
            ((Application) sAppContext).registerActivityLifecycleCallbacks(lifecycleCallbacks);

            LayoutInflater inflater = LayoutInflater.from(sAppContext);
            if (inflater.getFactory2() == null) {
                LayoutInflaterCompat.setFactory2(inflater, obtainInflaterFactory());
            }
        }

    }

    public static boolean setDefaultUiMode(@AppCompatDelegate.NightMode int mode) {
        AppCompatDelegate.setDefaultNightMode(mode); // 设置默认的日夜间模式
        return Utils.updateUiModeForApplication(sAppContext, mode);
    }

    public static void setUiMode(@AppCompatDelegate.NightMode int mode) {
        if (sAppContext == null) {
            HideLog.e(TAG, "Using the ui mode, you need to initialize");
            return;
        }

        boolean uiModeChange = setDefaultUiMode(mode);
        int appTheme = 0;
        // 应用Application
        if (uiModeChange) {
            appTheme = Utils.getManifestApplicationTheme(sAppContext);
            if (appTheme != 0) {
                sAppContext.getTheme().applyStyle(appTheme, true);
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
                    ((AppCompatActivity) next).getDelegate().applyDayNight();
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

        UiMode.dispatchApplyUiMode(get());
    }

    public static void applyUiModeViews(Activity activity) {
        UiMode.applyUiMode(activity, get());
    }

    /**
     * 适配切换的主题
     *
     * @param resId a theme style res id
     * @see #setUiMode(int)
     * @deprecated 为兼容v1.x版本保留的方法
     */
    @Deprecated
    public static void setTheme(int resId) {
        if (sAppContext == null) {
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
        sAppContext.setTheme(resId);

        // 执行View到对应主题
        UiMode.dispatchApplyUiMode(get());
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

    /**
     * <p> 设置 UiMode 相关的 Factory2 </p>
     * <p>
     * 如果还需要设置自己的 Factory, {@link com.aliya.uimode.factory.FactoryMerger}, 参考以下代码
     * </p>
     * <pre>
     *     LayoutInflater.Factory2 before = UiModeManager.obtainInflaterFactory();
     *     LayoutInflater.Factory2 after; // 赋值自己的Factory
     *     LayoutInflaterCompat.setFactory2(inflater, new FactoryMerger(before, after))
     * </pre>
     *
     * @param inflater {@link android.app.Activity#getLayoutInflater()}
     */
    public static void setInflaterFactor(LayoutInflater inflater) {
        if (sAppContext != null) {
            LayoutInflaterCompat.setFactory2(inflater, UiModeManager.obtainInflaterFactory());
        } else {
            HideLog.e(TAG, "Using the ui mode, you need to initialize");
        }
    }

    /**
     * 获取 Inflater Factory 实例
     *
     * @return LayoutInflaterFactory
     * @see #setInflaterFactor(LayoutInflater)
     */
    public static LayoutInflater.Factory2 obtainInflaterFactory() {
        return LayoutInflaterFactory.get();
    }

    /**
     * 设置日志 debug模式状态
     *
     * @param isDebug : false 强制关闭日志
     */
    public static void setLogDebug(boolean isDebug) {
        HideLog.setIsDebug(isDebug);
    }


    static class LayoutInflaterFactory {
        /**
         * 通过软引用单例来优化内存
         */
        static SoftReference<UiModeInflaterFactory> sSoftInstance;

        static UiModeInflaterFactory get() {
            UiModeInflaterFactory factory;
            if (sSoftInstance == null || (factory = sSoftInstance.get()) == null) {
                factory = new UiModeInflaterFactory(sFactory2);
                sSoftInstance = new SoftReference<>(factory);
            }
            return factory;
        }
    }

}
