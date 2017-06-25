package com.a_liya.uimode.mode;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.CallSuper;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.util.TypedValue;
import android.widget.ListView;

import com.a_liya.uimode.R;
import com.a_liya.uimode.utils.CheckUtils;

/**
 * ListView{@link android.widget.ListView} 的 UiMode {@link UiMode}实现类
 *
 * @author a_liYa
 * @date 2017/6/26 00:09.
 */
public class UiListView<T extends ListView> extends UiView<T> {

    protected int attrIdDivider;

    @CallSuper
    @Override
    public void assign(SparseArray<Integer> attrs) {
        super.assign(attrs);
        if (attrs != null) {
            attrIdDivider = attrs.get(R.styleable.SupportUiMode_android_divider, NO_ATTR_ID);
        }
    }

    @CallSuper
    @Override
    public <V extends T> void apply(V v, Resources.Theme theme) {
        super.apply(v, theme);
        if (v == null || theme == null) return;

        if (CheckUtils.residValid(attrIdDivider)) {
            int dividerHeight = v.getDividerHeight();
            theme.resolveAttribute(attrIdDivider, sOutValue, true);

            switch (sOutValue.type) {
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB4:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                    v.setDivider(new ColorDrawable(sOutValue.data));
                    break;
                case TypedValue.TYPE_STRING:
                    v.setDivider(ContextCompat.getDrawable(v.getContext(), sOutValue.resourceId));
                    break;
            }

            v.setDividerHeight(dividerHeight);
        }

    }
}
