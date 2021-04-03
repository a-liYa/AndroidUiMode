package com.aliya.uimode.apply;

import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;

/**
 * 应用android:textColorHint属性 {@link TextView}
 *
 * @author a_liYa
 * @date 2017/7/21 9:51.
 */
public final class ApplyTextColorHint extends AbsApply {

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (validArgs(v, entry) && v instanceof TextView) {
            switch (entry.getType()) {
                case Type.ATTR:
                    return applyAttr(v, entry);
                case Type.COLOR:
                case Type.DRAWABLE:
                    ((TextView) v).setHintTextColor(
                            ContextCompat.getColorStateList(v.getContext(), entry.getId()));
                    return true;
            }
        }
        return false;
    }

    @Override
    protected boolean applyAttr(View v, ResourceEntry entry) {
        if (validTheme(v) && resolveAttribute(v, entry.getId())) {
            switch (sOutValue.type) {
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB4:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                    ((TextView) v).setHintTextColor(sOutValue.data);
                    return true;
                case TypedValue.TYPE_STRING:
                    ((TextView) v).setHintTextColor(ContextCompat.
                            getColorStateList(v.getContext(), sOutValue.resourceId));
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
                    return true;
            }
        }
        return false;
    }
}
