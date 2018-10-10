package com.aliya.uimode.apply;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;

/**
 * 应用android:foreground属性 {@link View}
 *
 * @author a_liYa
 * @date 2017/6/26 13:04.
 */
public final class ApplyForeground extends AbsApply {

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (validArgs(v, entry)) {
            switch (entry.getType()) {
                case Type.ATTR:
                    return applyAttr(v, entry);
                case Type.COLOR:
                case Type.DRAWABLE:
                case Type.MIPMAP:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        v.setForeground(
                                ContextCompat.getDrawable(v.getContext(), entry.getId()));
                        return true;
                    } else if (v instanceof FrameLayout) {
                        ((FrameLayout) v).setForeground(
                                ContextCompat.getDrawable(v.getContext(), entry.getId()));
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    @Override
    protected boolean applyAttr(View v, ResourceEntry entry) {
        if (validTheme(v) && resolveAttribute(v, entry.getId())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB4:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                        v.setForeground(new ColorDrawable(sOutValue.data));
                        return true;
                    case TypedValue.TYPE_STRING:
                        v.setForeground(
                                ContextCompat.getDrawable(v.getContext(), sOutValue.resourceId));
                        return true;
                }
            } else if (v instanceof FrameLayout) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB4:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                        ((FrameLayout) v).setForeground(new ColorDrawable(sOutValue.data));
                        return true;
                    case TypedValue.TYPE_STRING:
                        ((FrameLayout) v).setForeground(
                                ContextCompat.getDrawable(v.getContext(), sOutValue.resourceId));
                        return true;
                }
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

}
