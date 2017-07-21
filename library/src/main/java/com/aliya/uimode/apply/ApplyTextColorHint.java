package com.aliya.uimode.apply;

import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

/**
 * 应用android:textColorHint属性 {@link EditText}
 *
 * @author a_liYa
 * @date 2017/6/26 12:51.
 */
public final class ApplyTextColorHint extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme) {
        if (argsValid(v, attrId, theme) && v instanceof EditText) {
            theme.resolveAttribute(attrId, sOutValue, true);
            switch (sOutValue.type) {
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB4:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                    ((EditText) v).setHintTextColor(sOutValue.data);
                    return true;
                case TypedValue.TYPE_STRING:
                    ((EditText) v).setHintTextColor(ContextCompat.
                            getColorStateList(v.getContext(), sOutValue.resourceId));
                    return true;
            }
        }
        return false;
    }
}
