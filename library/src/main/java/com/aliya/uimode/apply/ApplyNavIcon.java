package com.aliya.uimode.apply;

import android.support.annotation.AttrRes;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用app:navigationIcon属性 {@link android.support.v7.widget.Toolbar}
 *
 * @author a_liYa
 * @date 2017/6/30 14:37.
 */
public class ApplyNavIcon extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId) {
        if (argsValid(v, attrId) && v instanceof Toolbar) {
            if (getTheme(v).resolveAttribute(attrId, sOutValue, true)) {
                switch (sOutValue.type) {
                    case TypedValue.TYPE_STRING:
                        ((Toolbar) v).setNavigationIcon(sOutValue.resourceId);
                        return true;
                }
            }
        }
        return false;
    }

}
