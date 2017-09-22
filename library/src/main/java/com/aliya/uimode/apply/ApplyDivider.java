package com.aliya.uimode.apply;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;

/**
 * 应用android:divider属性 {@link android.widget.ListView}
 *
 * @author a_liYa
 * @date 2017/6/26 13:27.
 */
public final class ApplyDivider extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && v instanceof ListView) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                int dividerHeight = ((ListView) v).getDividerHeight();
                switch (sOutValue.type) {
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB4:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                        ((ListView) v).setDivider(new ColorDrawable(sOutValue.data));
                        ((ListView) v).setDividerHeight(dividerHeight);
                        return true;
                    case TypedValue.TYPE_STRING:
                        ((ListView) v).setDivider(ContextCompat
                                .getDrawable(v.getContext(), sOutValue.resourceId));
                        ((ListView) v).setDividerHeight(dividerHeight);
                        return true;
                }
            }
        }
        return false;
    }
}
