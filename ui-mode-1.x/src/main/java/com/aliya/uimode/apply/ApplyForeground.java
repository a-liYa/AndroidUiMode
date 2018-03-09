package com.aliya.uimode.apply;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用android:foreground属性 {@link android.view.View}
 *
 * @author a_liYa
 * @date 2017/6/26 13:04.
 */
public final class ApplyForeground extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB4:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                        v.setForeground(new ColorDrawable(sOutValue.data));
                        return true;
                    case TypedValue.TYPE_STRING:
                        v.setForeground(
                                ContextCompat.getDrawable(v.getContext(), sOutValue.resourceId));
                        return true;
                }
            }
        }
        return false;
    }

}
