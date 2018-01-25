package com.aliya.uimode.apply;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * 应用android:drawableRight {@link TextView}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public final class ApplyDrawableRight extends AbsApplyTextViewDrawable {

    @Override
    protected void setDrawablePolicy(TextView v, Drawable drawable) {
        setCompoundDrawables(v, null, null, drawable, null);
    }

}