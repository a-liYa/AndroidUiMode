package com.aliya.uimode.apply;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;

/**
 * 应用{@link TextView}的以下4个属性
 * <ul>
 * <li>android:drawableLeft</li>
 * <li>android:drawableTop</li>
 * <li>android:drawableRight</li>
 * <li>android:drawableBottom</li>
 * </ul>
 *
 * @author a_liYa
 * @date 2018/1/25 10:51.
 */
public abstract class AbsApplyTextViewDrawable extends AbsApply {

    protected abstract void setDrawablePolicy(TextView v, Drawable drawable);

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (validArgs(v, entry) && v instanceof TextView) {
            switch (entry.getType()) {
                case Type.ATTR:
                    return applyAttr(v, entry);
                case Type.COLOR:
                case Type.DRAWABLE:
                case Type.MIPMAP:
                    setDrawablePolicy((TextView) v,
                            ContextCompat.getDrawable(v.getContext(), entry.getId()));
                    return true;
            }
        }
        return false;
    }

    @Override
    protected boolean applyAttr(View v, ResourceEntry entry) {
        if (validTheme(v) && resolveAttribute(v, entry.getId())) {
            switch (sOutValue.type) {
                case TypedValue.TYPE_STRING:
                    setDrawablePolicy((TextView) v,
                            ContextCompat.getDrawable(v.getContext(), sOutValue.resourceId));
                    return true;
            }
        }
        return super.applyAttr(v, entry);
    }

    @Override
    public boolean isSupportType(String type) {
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case Type.ATTR:
                case Type.COLOR:
                case Type.DRAWABLE:
                case Type.MIPMAP:
                    return true;
            }
        }
        return false;
    }

    protected void setCompoundDrawables(TextView v, Drawable left, Drawable top, Drawable right,
                                        Drawable bottom) {
        Drawable[] drawables = v.getCompoundDrawables();
        if (left == null) {
            left = drawables[0];
        }
        if (top == null) {
            top = drawables[1];
        }
        if (right == null) {
            right = drawables[2];
        }
        if (bottom == null) {
            bottom = drawables[3];
        }
        v.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

}
