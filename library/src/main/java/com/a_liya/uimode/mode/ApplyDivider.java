package com.a_liya.uimode.mode;

import android.content.res.Resources;
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
public class ApplyDivider extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme) {
        if (argsValid(v, attrId, theme) && v instanceof ListView) {
            int dividerHeight = ((ListView) v).getDividerHeight();
            theme.resolveAttribute(attrId, sOutValue, true);

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
        return false;
    }
}
