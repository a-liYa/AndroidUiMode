package com.aliya.uimode.factory;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.aliya.uimode.intef.InflaterSupport;
import com.aliya.uimode.intef.UiModeChangeListener;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.UiMode;
import com.aliya.uimode.utils.ViewInflater;
import com.aliya.uimode.widget.MaskImageView;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, Integer> sAttrIdsMap = new HashMap<>();

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
                 * @see android.support.v7.app.AppCompatViewInflater
                 */
                if (context instanceof AppCompatActivity) {
                    AppCompatDelegate delegate = ((AppCompatActivity) context).getDelegate();
                    view = delegate.createView(parent, name, context, attrs);
                }
                break;
        }

        sAttrIdsMap.clear();
        if (mInflaterSupport != null) {
            final int N = attrs.getAttributeCount();
            for (int i = 0; i < N; i++) {
                String attrName = attrs.getAttributeName(i);
                if (mInflaterSupport.isSupportApply(attrName)) {
                    if (Attr.INVALIDATE.equals(attrName)
                            && attrs.getAttributeBooleanValue(i, false)) {
                        sAttrIdsMap.put(attrName, UiMode.NO_ATTR_ID);
                        continue;
                    }
                    int attrValue = parseAttrValue(attrs.getAttributeValue(i));
                    if (UiMode.attrIdValid(attrValue)) {
                        sAttrIdsMap.put(attrName, attrValue);
                    }
                }
            }
        }

        if (!sAttrIdsMap.isEmpty()) {

            final Map<String, Integer> attrIds = new HashMap<>(sAttrIdsMap.size());
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
                    // nothing to do
                }
            }
        }

        return view;
    }

    /**
     * 拦截处理指定的View
     */
    private void interceptHandler(View view, String name, Context context, AttributeSet attrs) {
//        if (view instanceof SwipeRefreshLayout) { // 适配SwipeRefreshLayout
//            UiModeManager
//                    .fitUiModeForSwipeRefreshLayout((SwipeRefreshLayout) view, context.getTheme
//        }
    }

    /**
     * 是否需要拦截创建
     *
     * @param name XML中的标签名即View的类名
     * @return true 需要拦截
     */
    private boolean isNeedInterceptByName(String name) {
        if (TextUtils.isEmpty(name)) return false;
        switch (name) {
            case "android.support.v4.widget.SwipeRefreshLayout":
                return true;
        }
        return false;
    }

    private int parseAttrValue(String attrVal) {
        if (!TextUtils.isEmpty(attrVal) && attrVal.startsWith("?")) {
            String subStr = attrVal.substring(1, attrVal.length());
            try {
                Integer attrId = Integer.valueOf(subStr);
                if (mInflaterSupport != null && mInflaterSupport.isSupportAttrId(attrId)) {
                    return attrId;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return UiMode.NO_ATTR_ID;
    }

}
