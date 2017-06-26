package com.a_liya.uimode.mode;

import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用android:background属性 {@link android.view.View}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public class ApplyBackground extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme) {
        if (argsValid(v, attrId, theme)) {
            theme.resolveAttribute(attrId, sOutValue, true);
            switch (sOutValue.type) {
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB4:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                    v.setBackgroundColor(sOutValue.data);
                    return true;
                case TypedValue.TYPE_STRING:
                    v.setBackgroundResource(sOutValue.resourceId);
                    return true;
            }

        }
        return false;
    }
}
