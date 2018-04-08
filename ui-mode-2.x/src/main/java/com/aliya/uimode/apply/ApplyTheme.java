package com.aliya.uimode.apply;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.aliya.uimode.mode.ResourceEntry;
import com.aliya.uimode.mode.Type;

/**
 * 应用android:theme {@link View}
 *
 * @author a_liYa
 * @date 2017/9/22 上午10:03.
 */
public class ApplyTheme extends AbsApply {

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (validArgs(v, entry)) {
            // 此处当子View仍需要主题支持时，可能因为遍历顺序造成不生效问题
            switch (entry.getType()) {
                case Type.ATTR:
                    return applyAttr(v, entry);
                case Type.STYLE:
                    getTheme(v).applyStyle(entry.getId(), true);
                    return true;
            }
        }
        return false;
    }

    @Override
    protected boolean applyAttr(View v, ResourceEntry entry) {
        if (validTheme(v) &&
                getActivityTheme(v).resolveAttribute(entry.getId(), sOutValue, true)) {
            if (sOutValue.type == TypedValue.TYPE_REFERENCE) {
                getTheme(v).applyStyle(sOutValue.resourceId, true);
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
                case Type.STYLE:
                    return true;
            }
        }
        return false;
    }

    private Resources.Theme getActivityTheme(View v) {
        Context context = v.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return context.getTheme();
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return context.getTheme();
    }

}
