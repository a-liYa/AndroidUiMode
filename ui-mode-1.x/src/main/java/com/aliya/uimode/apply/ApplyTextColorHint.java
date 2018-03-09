package com.aliya.uimode.apply;

import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * 应用android:textColorHint属性 {@link android.widget.TextView}
 *
 * @author a_liYa
 * @date 2017/7/21 9:51.
 */
public final class ApplyTextColorHint extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && v instanceof TextView) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB4:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                        ((TextView) v).setHintTextColor(sOutValue.data);
                        return true;
                    case TypedValue.TYPE_STRING:
                        ((TextView) v).setHintTextColor(ContextCompat.
                                getColorStateList(v.getContext(), sOutValue.resourceId));
                        return true;
                }
            }
        }
        return false;
    }
}
