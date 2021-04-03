package com.aliya.uimode.apply;

import android.view.View;

import com.aliya.uimode.mode.ResourceEntry;

/**
 * 调用View.invalidate()方法 {@code android.view.View.invalidate()}
 *
 * @author a_liYa
 * @date 2017/6/26 12:59.
 */
public final class ApplyInvalidate extends AbsApply {

    @Override
    public boolean onApply(View v, ResourceEntry entry) {
        if (v != null) {
            v.invalidate(); // 刷新View
            return true;
        }
        return false;
    }

    @Override
    public boolean isSupportType(String type) {
        return true;
    }

}
