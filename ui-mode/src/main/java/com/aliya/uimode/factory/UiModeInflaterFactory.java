package com.aliya.uimode.factory;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aliya.uimode.InflaterSupportImpl;
import com.aliya.uimode.R;
import com.aliya.uimode.intef.InflaterSupport;
import com.aliya.uimode.intef.UiModeChangeListener;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.UiMode;
import com.aliya.uimode.utils.ViewInflater;
import com.aliya.uimode.widget.MaskDrawable;
import com.aliya.uimode.widget.MaskHelper;
import com.aliya.uimode.widget.MaskImageView;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import static com.aliya.uimode.utils.UiModes.correctConfigUiMode;

/**
 * Xml创建View拦截器 - Factory2
 *
 * @author a_liYa
 * @date 2016/11/24 19:20.
 */
public class UiModeInflaterFactory implements LayoutInflater.Factory2 {

    private static ThreadLocal<Map<String, ResourceEntry>> sAttrIdsLocal = new ThreadLocal<>();

    private InflaterSupport mInflaterSupport;
    private LayoutInflater.Factory2 mInflaterFactory;

    public UiModeInflaterFactory(LayoutInflater.Factory2 factory) {
        mInflaterFactory = factory;
        mInflaterSupport = new InflaterSupportImpl();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
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
                Activity activity = UiMode.findActivity(context);
                if (activity instanceof AppCompatActivity) {
                    /**
                     * @see androidx.appcompat.app.AppCompatDelegateImpl#createView(View, String, Context, AttributeSet)
                     */
                    AppCompatDelegate delegate = ((AppCompatActivity) activity).getDelegate();
                    view = delegate.createView(parent, name, context, attrs);
                }
                if (view == null) {
                    if (mInflaterFactory != null) {
                        view = mInflaterFactory.onCreateView(parent, name, context, attrs);
                    }
                }
                break;
        }

        String ignoreValue = null;

        Map<String, ResourceEntry> attrIdsMap = sAttrIdsLocal.get();
        if (attrIdsMap == null) {
            sAttrIdsLocal.set(attrIdsMap = new HashMap<>());
        }
        attrIdsMap.clear();

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
                        attrIdsMap.put(attrName, null);
                        continue;
                    }

                    String attrValue = attrs.getAttributeValue(i);

                    // 解析 ?attr
                    int attrId = parseAttrId(attrValue);
                    if (UiMode.idValid(attrId)) {
                        ResourceEntry entry = new ResourceEntry(attrId, context);
                        if (mInflaterSupport.isSupportApplyType(attrName, entry.getType())) {
                            attrIdsMap.put(attrName, entry);
                        }
                        continue;
                    }

                    // 解析 @drawable、@color、@theme、@mipmap 等等
                    int resId = parseResId(attrValue);
                    if (UiMode.idValid(resId)) {
                        ResourceEntry entry = new ResourceEntry(resId, context);
                        if (mInflaterSupport.isSupportApplyType(attrName, entry.getType())) {
                            attrIdsMap.put(attrName, entry);
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
                    attrIdsMap.remove(ignore);
                }
            }
        }

        if (!attrIdsMap.isEmpty()) {

            // view == null, 必须在 view 创建之前复制数据
            final Map<String, ResourceEntry> attrIdsCopy = new HashMap<>(attrIdsMap);
            attrIdsMap.clear();

            if (view == null) { // 系统没有创建
                view = ViewInflater.createViewFromTag(name, context, attrs);
            }

            if (view != null) {
                UiMode.saveViewAndAttrs(context, view, attrIdsCopy); // 缓存View
            }
        } else { //  实现UiModeChangeListener接口的View
            if (view != null) {
                if (view instanceof UiModeChangeListener)
                    UiMode.saveView(context, view);
            } else {
                try {
                    Class<?> clazz = Class.forName(name);
                    if (UiModeChangeListener.class.isAssignableFrom(clazz)) {
                        view = ViewInflater.createViewFromTag(name, context, attrs);
                        UiMode.saveView(context, view); // 缓存View
                    }
                } catch (Exception e) {
                    // no-op
                }
            }
        }

        return onInterceptView(context, attrs, view);
    }

    private View onInterceptView(Context context, AttributeSet attrs, View view) {
        if (view instanceof TextView) {
            MaskHelper maskHelper = new MaskHelper(context, attrs);
            view.setTag(R.id.tag_ui_mode_mask_drawable, maskHelper);
            Drawable[] drawables = ((TextView) view).getCompoundDrawables();
            boolean ifTrue = false;
            for (int i = 0; i < drawables.length; i++) {
                if (drawables[i] != null) {
                    drawables[i] = new MaskDrawable(drawables[i], maskHelper);
                    ifTrue = true;
                }
            }
            if (ifTrue) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(
                        drawables[0], drawables[1], drawables[2], drawables[3]);
            }
        }
        return view;
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
                return Integer.valueOf(subStr);
            } catch (Exception e) {
                // no-op
            }
        }
        return UiMode.NO_ID;
    }

}
