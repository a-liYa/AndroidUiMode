package com.aliya.uimode.apply;

import android.support.annotation.AttrRes;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用android:theme {@link android.view.View}
 *
 * @author a_liYa
 * @date 2017/9/22 上午10:03.
 */
public class ApplyTheme extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
            if (sOutValue.type == TypedValue.TYPE_REFERENCE) {
                getTheme(v).applyStyle(sOutValue.resourceId, true);
            }
        }
        return false;
    }
}
