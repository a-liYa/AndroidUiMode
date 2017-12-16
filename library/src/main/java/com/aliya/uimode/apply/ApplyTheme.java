package com.aliya.uimode.apply;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用android:theme {@link android.view.View}
 *
 * @author a_liYa
 * @date 2017/9/22 上午10:03.
 */
public class ApplyTheme extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && getActivityTheme(v).resolveAttribute(attrId, sOutValue, true)) {
            if (sOutValue.type == TypedValue.TYPE_REFERENCE) {
                getTheme(v).applyStyle(sOutValue.resourceId, true);
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
