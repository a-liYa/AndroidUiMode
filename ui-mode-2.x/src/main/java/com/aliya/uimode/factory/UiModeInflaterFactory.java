package com.aliya.uimode.factory;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.aliya.uimode.intef.InflaterSupport;
import com.aliya.uimode.intef.UiModeChangeListener;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.UiMode;
import com.aliya.uimode.utils.ViewInflater;
import com.aliya.uimode.widget.MaskImageView;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

/**
 * Xml创建View拦截器 - Factory
 *
 * @author a_liYa
 * @date 2016/11/24 19:20.
 */
public class UiModeInflaterFactory implements LayoutInflaterFactory {

    /**
     * 通过软引用单例来优化内存
     */
    private static SoftReference<UiModeInflaterFactory> sSoftInstance;

    private static Map<String, ResourceEntry> sAttrIdsMap = new HashMap<>();

    private InflaterSupport mInflaterSupport;

    public static UiModeInflaterFactory get(InflaterSupport support) {
        UiModeInflaterFactory factory;
        if (sSoftInstance == null || (factory = sSoftInstance.get()) == null) {
            sSoftInstance = new SoftReference<>(factory = new UiModeInflaterFactory(support));
        }
        return factory;
    }

    public UiModeInflaterFactory(InflaterSupport support) {
        mInflaterSupport = support;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return uiModeCreateView(parent, name, context, attrs);
    }

    /**
     * 拦截创建具有UiMode属性的View, 通过{@link View#setTag(int, Object)}携带属性资源
     *
     * @param parent  parent
     * @param name    class name
     * @param context context
     * @param attrs   AttributeSet
     * @return 返回创建的View
     */
    private View uiModeCreateView(View parent, String name, Context context, AttributeSet attrs) {

        correctConfigUiMode(context);

        View view = null;
        switch (name) { // 拦截所有的ImageView、AppCompatImageView
            case "ImageView":
            case "android.support.v7.widget.AppCompatImageView":
                view = new MaskImageView(context, attrs);
                break;
            default:
                /**
                 * @see android.support.v7.app.AppCompatDelegateImplV9#createView(View, String,
                 * Context, AttributeSet)
                 */
                if (context instanceof AppCompatActivity) {
                    AppCompatDelegate delegate = ((AppCompatActivity) context).getDelegate();
                    view = delegate.createView(parent, name, context, attrs);
                }
                break;
        }

        String ignoreValue = null;
        sAttrIdsMap.clear();
        if (mInflaterSupport != null) {
            final int N = attrs.getAttributeCount();
            for (int i = 0; i < N; i++) {
                String attrName = attrs.getAttributeName(i);
                if (Attr.IGNORE.equals(attrName)) {
                    ignoreValue = attrs.getAttributeValue(i);
                    continue;
                }
                if (mInflaterSupport.isSupportApply(attrName)) {

                    if (Attr.INVALIDATE.equals(attrName)
                            && attrs.getAttributeBooleanValue(i, false)) {
                        sAttrIdsMap.put(attrName, null);
                        continue;
                    }

                    String attrValue = attrs.getAttributeValue(i);

                    // 解析 ?attr
                    int attrId = parseAttrId(attrValue);
                    if (UiMode.idValid(attrId)) {
                        try {
                            String attrType = context.getResources().getResourceTypeName(attrId);
                            if (mInflaterSupport.isSupportApplyType(attrName, attrType)) {
                                sAttrIdsMap.put(attrName, new ResourceEntry(attrId, attrType));
                            }
                        } catch (Resources.NotFoundException e) {
                            // no-op
                        }
                        continue;
                    }

                    // 解析 @drawable、@color、@theme、@mipmap 等等
                    int resId = parseResId(attrValue);
                    if (UiMode.idValid(resId)) {
                        try {
                            String attrType = context.getResources().getResourceTypeName(resId);
                            if (mInflaterSupport.isSupportApplyType(attrName, attrType)) {
                                sAttrIdsMap.put(attrName, new ResourceEntry(resId, attrType));
                            }
                        } catch (Resources.NotFoundException e) {
                            // no-op
                        }
                        continue;
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(ignoreValue)) {
            String[] ignores = ignoreValue.split("\\|");
            for (String ignore : ignores) {
                if (!TextUtils.isEmpty(ignore = ignore.trim())) {
                    sAttrIdsMap.remove(ignore);
                }
            }
        }

        if (!sAttrIdsMap.isEmpty()) {

            final Map<String, ResourceEntry> attrIds = new HashMap<>(sAttrIdsMap.size());
            attrIds.putAll(sAttrIdsMap);

            if (view == null) { // 系统没有创建
                view = ViewInflater.createViewFromTag(context, name, attrs);
            }

            if (view != null) {
                UiMode.saveViewAndAttrIds(context, view, attrIds); // 缓存View
            }
        } else { //  实现UiModeChangeListener接口的View
            if (view != null) {
                if (view instanceof UiModeChangeListener)
                    UiMode.saveView(context, view);
            } else {
                try {
                    Class<?> clazz = Class.forName(name);
                    if (UiModeChangeListener.class.isAssignableFrom(clazz)) {
                        view = ViewInflater.createViewFromTag(context, name, attrs);
                        UiMode.saveView(context, view); // 缓存View
                    }
                } catch (Exception e) {
                    // no-op
                }
            }
        }

        return view;
    }

    /**
     * 纠正 {@link Configuration#uiMode} 的值.
     * 在xml中遇到WeView时会被改成 {@link Configuration#UI_MODE_NIGHT_NO}, 导致后续View出现问题.
     *
     * @param context .
     */
    private void correctConfigUiMode(Context context) {
        /**
         * 代码参考自 {@link android.support.v7.app.AppCompatDelegateImplV14#updateForNightMode(int)}
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

    private int parseAttrId(String attrVal) {
        if (!TextUtils.isEmpty(attrVal) && attrVal.startsWith("?")) {
            String subStr = attrVal.substring(1, attrVal.length());
            try {
                Integer attrId = Integer.valueOf(subStr);
                if (mInflaterSupport != null && mInflaterSupport.isSupportAttrId(attrId)) {
                    return attrId;
                }
            } catch (Exception e) {
                // no-op
            }
        }
        return UiMode.NO_ID;
    }

    private int parseResId(String attrVal) {
        if (!TextUtils.isEmpty(attrVal) && attrVal.startsWith("@")) {
            String subStr = attrVal.substring(1, attrVal.length());
            try {
                Integer attrId = Integer.valueOf(subStr);
                return attrId;
            } catch (Exception e) {
                // no-op
            }
        }
        return UiMode.NO_ID;
    }

}
