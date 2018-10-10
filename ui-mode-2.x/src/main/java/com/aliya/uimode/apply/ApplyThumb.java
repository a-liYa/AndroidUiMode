package com.aliya.uimode.apply;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsSeekBar;

import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;

/**
 * 应用android:thumb属性 {@link AbsSeekBar}
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public final class ApplyThumb extends AbsApply {

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (validArgs(v, entry) && v instanceof AbsSeekBar) {
            switch (entry.getType()) {
                case Type.ATTR:
                    return applyAttr(v, entry);
                case Type.COLOR:
                case Type.DRAWABLE:
                    ((AbsSeekBar) v).setThumb(
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
                    ((AbsSeekBar) v).setThumb(
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
                    return true;
            }
        }
        return false;
    }
}
