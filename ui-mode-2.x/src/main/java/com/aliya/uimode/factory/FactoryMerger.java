package com.aliya.uimode.factory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * <p>扩展 {@link LayoutInflater#setFactory2(LayoutInflater.Factory2)} 只能设置一个Factory的限制<p/>
 *
 * @author a_liYa
 * @date 2019/2/28 23:14.
 */
public final class FactoryMerger implements LayoutInflater.Factory2 {

    private LayoutInflater.Factory2 mBefore, mAfter;

    public FactoryMerger(LayoutInflater.Factory2 before, LayoutInflater.Factory2 after) {
        mBefore = before;
        mAfter = after;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if (mBefore != null) {
            View v = mBefore.onCreateView(name, context, attrs);
            if (v != null) return v;
        }
        return mAfter != null ? mAfter.onCreateView(name, context, attrs) : null;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (mBefore != null) {
            View v = mBefore.onCreateView(parent, name, context, attrs);
            if (v != null) return v;
        }
        return mAfter != null ? mAfter.onCreateView(parent, name, context, attrs) : null;
    }

}
