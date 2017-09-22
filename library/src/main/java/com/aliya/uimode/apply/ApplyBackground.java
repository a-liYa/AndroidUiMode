package com.aliya.uimode.apply;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用android:background属性 {@link android.view.View}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public final class ApplyBackground extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
            switch (sOutValue.type) {
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB4:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                    v.setBackgroundColor(sOutValue.data);
                    return true;
                case TypedValue.TYPE_STRING:
                    Drawable d = ContextCompat.getDrawable(v.getContext(), sOutValue.resourceId);
                    Drawable old = v.getBackground();
                    if (old != null && d != null) { // 传递 level
                        d.setLevel(old.getLevel());
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        v.setBackground(d);
                    } else {
                        v.setBackgroundDrawable(d);
                    }
                    // 没用以下方法，防止resourceId相等时设置背景无效（resId对应资源里的资源有变化）
//                        v.setBackgroundResource(sOutValue.resourceId);
                    return true;
            }
        }
        return false;
    }
}
