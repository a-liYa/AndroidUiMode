package com.aliya.uimode.apply;

import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * 应用android:textColor属性 {@link android.widget.TextView}
 *
 * @author a_liYa
 * @date 2017/6/26 12:51.
 */
public final class ApplyTextColor extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && v instanceof TextView) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB4:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                        ((TextView) v).setTextColor(sOutValue.data);
                        return true;
                    case TypedValue.TYPE_STRING:
                        ((TextView) v).setTextColor(ContextCompat.
                                getColorStateList(v.getContext(), sOutValue.resourceId));
                        return true;
                }
            }
        }
        return false;
    }
}
