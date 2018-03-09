package com.aliya.uimode.apply;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.AttrRes;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

/**
 * 应用android:src属性 {@link android.widget.ImageView}
 *
 * @author a_liYa
 * @date 2017/6/26 13:32.
 */
public final class ApplySrc extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && v instanceof ImageView) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB4:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                        ((ImageView) v).setImageDrawable(new ColorDrawable(sOutValue.data));
                        return true;
                    case TypedValue.TYPE_STRING:
                        ((ImageView) v).setImageResource(sOutValue.resourceId);
                        return true;
                }
            }
        }
        return false;
    }
}
