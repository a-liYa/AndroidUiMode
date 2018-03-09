package com.aliya.uimode.apply;

import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;

/**
 * 应用android:button {@link android.widget.CompoundButton}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public final class ApplyButton extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && v instanceof CompoundButton) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_STRING:
                        Drawable d = ContextCompat.getDrawable(
                                v.getContext(), sOutValue.resourceId);
                        if (d != null) {
                            Drawable old = v.getBackground();
                            if (old != null) { // 传递 level
                                d.setLevel(old.getLevel());
                            }
                            ((CompoundButton) v).setButtonDrawable(d);
                            return true;
                        }
                }
            }
        }
        return false;
    }
}
