package com.aliya.uimode.apply;

import android.support.annotation.AttrRes;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用android:alpha属性 {@link android.view.View}
 *
 * @author a_liYa
 * @date 2017/6/26 12:59.
 */
public final class ApplyAlpha extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId)) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                if (sOutValue.type == TypedValue.TYPE_FLOAT) {
                    v.setAlpha(sOutValue.getFloat());
                    return true;
                }
            }
        }
        return false;
    }

}
