package com.aliya.uimode.apply;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;

import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;

/**
 * 应用android:divider属性 {@link ListView}
 *
 * @author a_liYa
 * @date 2017/6/26 13:27.
 */
public final class ApplyDivider extends AbsApply {

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (validArgs(v, entry) && v instanceof ListView) {
            switch (entry.getType()) {
                case Type.ATTR:
                    return applyAttr(v, entry);
                case Type.COLOR:
                case Type.DRAWABLE:
                case Type.MIPMAP:
                    int dividerHeight = ((ListView) v).getDividerHeight();
                    ((ListView) v).setDivider(
                            ContextCompat.getDrawable(v.getContext(), entry.getId()));
                    ((ListView) v).setDividerHeight(dividerHeight);
                    return true;
            }
        }
        return false;
    }

    @Override
    protected boolean applyAttr(View v, ResourceEntry entry) {
        if (validTheme(v) && resolveAttribute(v, entry.getId())) {
            int dividerHeight = ((ListView) v).getDividerHeight();
            switch (sOutValue.type) {
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB4:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                    ((ListView) v).setDivider(new ColorDrawable(sOutValue.data));
                    ((ListView) v).setDividerHeight(dividerHeight);
                    return true;
                case TypedValue.TYPE_STRING:
                    ((ListView) v).setDivider(ContextCompat
                            .getDrawable(v.getContext(), sOutValue.resourceId));
                    ((ListView) v).setDividerHeight(dividerHeight);
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
}
