package com.aliya.uimode.apply;

import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用android:alpha属性 {@link android.view.View}
 *
 * @author a_liYa
 * @date 2017/6/26 12:59.
 */
public class ApplyAlpha extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme) {
        if (argsValid(v, attrId, theme)) {
            theme.resolveAttribute(attrId, sOutValue, true);
            if (sOutValue.type == TypedValue.TYPE_FLOAT) {
                v.setAlpha(sOutValue.getFloat());
                return true;
            }
        }
        return false;
    }

}
