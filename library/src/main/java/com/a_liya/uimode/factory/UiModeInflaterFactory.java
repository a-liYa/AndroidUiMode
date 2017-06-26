package com.a_liya.uimode.factory;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import com.a_liya.uimode.R;
import com.a_liya.uimode.UiModeManager;
import com.a_liya.uimode.mode.UiView;
import com.a_liya.uimode.widget.MaskImageView;

import java.lang.ref.SoftReference;

/**
 * 夜间模式拦截器 - Factory
 *
 * @author a_liYa
 * @date 2016/11/24 19:20.
 */
public class UiModeInflaterFactory implements LayoutInflaterFactory {

    /**
     * 通过软引用单例来优化内存
     */
    private static SoftReference<UiModeInflaterFactory> sSoftInstance;

    private static SparseArray<Integer> sAttrArrays = new SparseArray<>();

    public static UiModeInflaterFactory get() {
        UiModeInflaterFactory factory = null;
        if (sSoftInstance != null) {
            factory = sSoftInstance.get();
        }

        if (factory == null) {
            factory = new UiModeInflaterFactory();
            sSoftInstance = new SoftReference<>(factory);
        }
        return factory;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return uiModeCreateView(parent, name, context, attrs);
    }

    /**
     * 日夜间模式 拦截日夜间模式的View, 创建出来然后setTag携带对应的日夜间资源
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
                // AppCompatDelegateImplV7 -> AppCompatViewInflater 进行拦截View替换AppCompatView
                if (context instanceof AppCompatActivity) {
                    AppCompatDelegate delegate = ((AppCompatActivity) context).getDelegate();
                    view = delegate.createView(parent, name, context, attrs);
                }
                break;
        }

        sAttrArrays.clear();
//        UiModeBean uiModeBean = null;
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);

            if ("background".equals(attrName)) {
                int attrValue = parseAttrValue(attrs.getAttributeValue(i));
                if (attrValue != UiView.NO_ATTR_ID) {
                    sAttrArrays.put(attrs.getAttributeNameResource(i), attrValue);
                }
            }

//
//
//            if (UiModeManager.isBackground(attrName)) {
//                int attrValue = UiModeManager.parseAttrValue(attrs.getAttributeValue(i));
//                if (attrValue > 0) {
//                    if (uiModeBean == null) uiModeBean = new UiModeBean();
//                    uiModeBean.setBackgroundResId(attrValue);
//                }
//            } else if (UiModeManager.isTextColor(attrName)) {
//                int attrValue = UiModeManager.parseAttrValue(attrs.getAttributeValue(i));
//                if (attrValue > 0) {
//                    if (uiModeBean == null) uiModeBean = new UiModeBean();
//                    uiModeBean.setTextColorResId(attrValue);
//                }
//            } else if (UiModeManager.isDivider(attrName)) {
//                int attrValue = UiModeManager.parseAttrValue(attrs.getAttributeValue(i));
//                if (attrValue > 0) {
//                    if (uiModeBean == null) uiModeBean = new UiModeBean();
//                    uiModeBean.setDividerResId(attrValue);
//                }
//            } else if (UiModeManager.isAlpha(attrName)) {
//                int attrValue = UiModeManager.parseAttrValue(attrs.getAttributeValue(i));
//                if (attrValue > 0) {
//                    if (uiModeBean == null) uiModeBean = new UiModeBean();
//                    uiModeBean.setAlphaResId(attrValue);
//                }
//            } else if (UiModeManager.isSrc(attrName)) {
//                int attrValue = UiModeManager.parseAttrValue(attrs.getAttributeValue(i));
//                if (attrValue > 0) {
//                    if (uiModeBean == null) uiModeBean = new UiModeBean();
//                    uiModeBean.setSrcResId(attrValue);
//                }
//            } else if (UiModeManager.isNavigationIcon(attrName)) {
//                int attrValue = UiModeManager.parseAttrValue(attrs.getAttributeValue(i));
//                if (attrValue > 0) {
//                    if (uiModeBean == null) uiModeBean = new UiModeBean();
//                    uiModeBean.setNavIconResId(attrValue);
//                }
//            }
//        }
//
//        if (uiModeBean != null || isNeedInterceptByName(name)) {
//            if (view == null) { // 系统没有拦截创建
//                view = ViewInflater.createViewFromTag(context, name, attrs);
//            }
//
//            if (view != null) {
//                if (uiModeBean != null) {
//                    view.setTag(R.id.tag_ui_mode, uiModeBean);
//                }
//                interceptHandler(view, name, context, attrs);
//            }
        }
        return view;
    }

    /**
     * 拦截处理指定的View
     */
    private void interceptHandler(View view, String name, Context context, AttributeSet attrs) {

        if (view instanceof SwipeRefreshLayout) { // 适配SwipeRefreshLayout
//            UiModeManager
//                    .fitUiModeForSwipeRefreshLayout((SwipeRefreshLayout) view, context.getTheme
// ());
        }
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

    private static int parseAttrValue(String attrVal) {
        return UiModeManager.parseAttrValue(attrVal);
    }

//    /**
//     * 通过反射创建View
//     */
//    private View reflectiveCreateView(String name, String prefix, Context context, AttributeSet
//            attrs) {
//        Constructor<? extends View> constructor;
//        Class<? extends View> clazz;
//
//        try {
//            clazz = context.getClassLoader().loadClass(
//                    prefix != null ? (prefix + name) : name).asSubclass(View.class);
//            constructor = clazz.getConstructor(sConstructorSignature);
//            constructor.setAccessible(true);
//            return constructor.newInstance(context, attrs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
