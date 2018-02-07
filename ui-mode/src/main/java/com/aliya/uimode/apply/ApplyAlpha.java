package com.aliya.uimode.apply;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;

/**
 * 应用android:alpha属性 {@link View}
 *
 * @author a_liYa
 * @date 2017/6/26 12:59.
 */
public final class ApplyAlpha extends AbsApply {

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (validArgs(v, entry)) {
            switch (entry.getType()) {
                case Type.ATTR:
                    return applyAttr(v, entry);
                case Type.STRING:
                    float alpha = -1;
                    try {
                        alpha = Float.parseFloat(v.getResources().getString(entry.getId()));
                    } catch (NumberFormatException e) {
                        // no-op
                    } catch (Resources.NotFoundException e) {
                        // no-op
                    }
                    if (alpha >= 0 && alpha <= 1) {
                        v.setAlpha(alpha);
                    }
                    return true;
            }
        }
        return false;
    }

    @Override
    protected boolean applyAttr(View v, ResourceEntry entry) {
        if (validTheme(v) && resolveAttribute(v, entry.getId(), sOutValue, true)) {
            if (sOutValue.type == TypedValue.TYPE_FLOAT) {
                v.setAlpha(sOutValue.getFloat());
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
                case Type.STRING:
                    return true;
            }
        }
        return false;
    }

}
