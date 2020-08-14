package com.aliya.uimode.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.annotation.AnyRes;
import androidx.annotation.AttrRes;
import androidx.appcompat.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.aliya.uimode.UiModeManager;
import com.aliya.uimode.intef.UiApply;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;
import com.aliya.uimode.mode.UiMode;

import java.util.Map;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

/**
 * 帮助解析资源保存资源 - 工具类
 *
 * @author a_liYa
 * @date 2018/12/17 21:44.
 */
public class UiModes {

    /**
     * ImageView应用src属性， 并加入到UiMode列表
     *
     * @param v      ImageView
     * @param attrId .
     * @see #applySave(View, String, int)
     * @deprecated 过时方法，2.x版本不推荐使用 ATTR
     */
    @Deprecated
    public static void applyImageSrc(ImageView v, @AttrRes int attrId) {
        if (v == null) return;

        UiApply uiApply = UiModeManager.get().obtainApplyPolicy(Attr.NAME_SRC);
        if (uiApply != null) {
            ResourceEntry entry = new ResourceEntry(attrId, Type.ATTR);
            if (uiApply.onApply(v, entry)) {
                saveViewUiMode(v, Attr.builder().add(Attr.NAME_SRC, entry).build());
            }
        }
    }

    /**
     * 应用属性，并加入到UiMode列表
     *
     * @param v        a view
     * @param attrName attr name {@link Attr}
     * @param resId    resource reference, such as R.color.x or R.mipmap.x or R.drawable.x
     *                 or R.string.x or R.attr.x or R.style.x or R.dimen.x, etc.
     */
    public static void applySave(View v, String attrName, @AnyRes int resId) {
        if (v == null) return;

        UiApply uiApply = UiModeManager.get().obtainApplyPolicy(attrName);
        if (uiApply != null) {
            ResourceEntry entry = new ResourceEntry(resId, v.getContext());
            if (uiApply.isSupportType(entry.getType())) {
                if (uiApply.onApply(v, entry)) {
                    UiMode.saveViewAndAttrs(
                            v.getContext(), v, Attr.builder().add(attrName, entry).build());
                }
            }
        }
    }

    /**
     * 供外部使用，添加 通过new创建具有UiMode属性的View
     *
     * @param v     a view
     * @param attrs 建议通过 {@link Attr#builder()} 创建
     */
    public static void saveViewUiMode(View v, Map<String, ResourceEntry> attrs) {
        if (v == null) return;
        UiMode.saveViewAndAttrs(v.getContext(), v, attrs);
    }

    /**
     * 纠正 {@link Configuration#uiMode} 的值.
     * 在xml中遇到WeView时会被改成 {@link Configuration#UI_MODE_NIGHT_NO}, 导致后续View出现问题.
     *
     * @param context .
     */
    public static void correctConfigUiMode(Context context) {
        /**
         * 参考自 {@link androidx.appcompat.app.AppCompatDelegateImplV14#updateForNightMode(int)}
         */
        final Resources res = context.getResources();
        final Configuration conf = res.getConfiguration();
        final int uiMode = (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES)
                ? Configuration.UI_MODE_NIGHT_YES
                : Configuration.UI_MODE_NIGHT_NO;
        if ((conf.uiMode & Configuration.UI_MODE_NIGHT_MASK) != uiMode) {
            final Configuration config = new Configuration(conf);
            final DisplayMetrics metrics = res.getDisplayMetrics();

            // Update the UI Mode to reflect the new night mode
            config.uiMode = uiMode | (config.uiMode & ~Configuration.UI_MODE_NIGHT_MASK);
            res.updateConfiguration(config, metrics);
        }
    }

    /**
     * 判断当前 context 是否为夜间模式.
     *
     * @param context The current context.
     * @return true : 表示为夜间模式.
     */
    public static boolean isUiModeNight(Context context) {
        final Configuration config = context.getResources().getConfiguration();
        final int currentUiMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return Configuration.UI_MODE_NIGHT_YES == currentUiMode;
    }

}
