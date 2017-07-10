package com.aliya.uimode.apply;

import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.view.View;

/**
 * 调用View.invalidate()方法 {@code android.view.View.invalidate()}
 *
 * @author a_liYa
 * @date 2017/6/26 12:59.
 */
public final class ApplyInvalidate extends AbsApply {

    @Override
    public boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme) {
        if (v != null) {
            v.invalidate(); // 刷新View
            return true;
        }
        return false;
    }

}
