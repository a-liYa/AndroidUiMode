package com.aliya.uimode.apply;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * 应用android:drawableTop {@link android.widget.TextView}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public final class ApplyDrawableTop extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme) {
        if (argsValid(v, attrId, theme) && v instanceof TextView) {
            if (theme.resolveAttribute(attrId, sOutValue, true)) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_STRING:
                        Drawable top = ContextCompat.getDrawable(v.getContext(),
                                sOutValue.resourceId);
                        setCompoundDrawables((TextView) v, top);
                        return true;
                }
            }
        }
        return false;
    }

    private void setCompoundDrawables(TextView v, Drawable top) {
        Drawable[] drawables = v.getCompoundDrawables();
        if (drawables == null) {
            v.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        } else {
            v.setCompoundDrawablesWithIntrinsicBounds(drawables[0], top, drawables[2],
                    drawables[3]);
        }
    }

}
