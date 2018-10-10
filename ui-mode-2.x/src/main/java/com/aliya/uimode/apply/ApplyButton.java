package com.aliya.uimode.apply;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;

import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;

/**
 * 应用android:button {@link CompoundButton}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public final class ApplyButton extends AbsApply {

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (validArgs(v, entry) && v instanceof CompoundButton) {
            switch (entry.getType()) {
                case Type.ATTR:
                    return applyAttr(v, entry);
                case Type.COLOR:
                case Type.DRAWABLE:
                case Type.MIPMAP:
                    ((CompoundButton) v).setButtonDrawable(entry.getId());
                    return true;
            }
        }
        return false;
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

    @Override
    protected boolean applyAttr(View v, ResourceEntry entry) {
        if (validTheme(v) && resolveAttribute(v, entry.getId())) {
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
        return super.applyAttr(v, entry);
    }
}
