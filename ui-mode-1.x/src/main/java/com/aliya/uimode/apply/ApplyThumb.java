package com.aliya.uimode.apply;

import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsSeekBar;

/**
 * 应用android:thumb属性 {@link android.widget.AbsSeekBar}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public final class ApplyThumb extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && v instanceof AbsSeekBar) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_STRING:
                        Drawable d = ContextCompat.getDrawable(v.getContext(),
                                sOutValue.resourceId);
                        ((AbsSeekBar) v).setThumb(d);
                        return true;
                }
            }
        }
        return false;
    }
}
