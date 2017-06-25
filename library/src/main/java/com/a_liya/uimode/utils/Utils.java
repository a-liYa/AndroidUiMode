package com.a_liya.uimode.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.TypedValue;

import com.a_liya.uimode.R;

/**
 * 兼容工具
 *
 * @author a_liYa
 * @date 2017/6/23 12:34.
 */
public class Utils {

    private static TypedValue sTempValue;

    public static final Drawable getDrawableCompat(Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return context.getResources().getDrawable(id);
        } else {
            // Prior to JELLY_BEAN, Resources.getDrawable() would not correctly
            // retrieve the final configuration density when the resource ID
            // is a reference another Drawable resource. As a workaround, try
            // to resolve the drawable reference manually.
            final int resolvedId;
            synchronized (Utils.class) {
                if (sTempValue == null) {
                    sTempValue = new TypedValue();
                }
                context.getResources().getValue(id, sTempValue, true);
                resolvedId = sTempValue.resourceId;
            }
            return context.getResources().getDrawable(resolvedId);
        }
    }

    /**
     * 解析属性值对应真正的资源id
     *
     * @param attrVal 属性值（String）
     * @return 资源id
     */
    public static int parseAttrValue(String attrVal) {
        if (TextUtils.isEmpty(attrVal)) return -1;
        if (attrVal.startsWith("?")) {
            String subStr = attrVal.substring(1, attrVal.length());
            try {
                int resId = Integer.valueOf(subStr);

                for (int i = 0; i < R.styleable.UiMode.length; i++) {
                    if (R.styleable.UiMode[i] == resId) {
                        return resId;
                    }
                }
                if (R.attr.colorPrimary == resId) {
                    return resId;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

}
