package com.a_liya.uimode.apply;

import android.content.res.Resources;
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
public class ApplySrc extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme) {
        if (argsValid(v, attrId, theme) && v instanceof ImageView) {
            theme.resolveAttribute(attrId, sOutValue, true);
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
        return false;
    }
}
