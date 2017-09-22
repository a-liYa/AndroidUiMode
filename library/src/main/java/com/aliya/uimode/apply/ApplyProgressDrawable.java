package com.aliya.uimode.apply;

import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;

/**
 * 应用android:progressDrawable属性 {@link android.widget.ProgressBar}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public final class ApplyProgressDrawable extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && v instanceof ProgressBar) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_STRING:
                        Drawable d = ContextCompat.getDrawable(v.getContext(),
                                sOutValue.resourceId);
                        ((ProgressBar) v).setProgressDrawable(d);
                        return true;
                }
            }
        }
        return false;
    }
}
