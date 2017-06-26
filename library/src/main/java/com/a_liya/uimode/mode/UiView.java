package com.a_liya.uimode.mode;

import android.content.res.Resources.Theme;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.CallSuper;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;

import com.a_liya.uimode.R;
import com.a_liya.uimode.utils.CheckUtils;
import com.a_liya.uimode.utils.Utils;

/**
 * View{@link android.view.View} 的 UiMode {@link UiMode}实现类
 *
 * @author a_liYa
 * @date 2017/6/23 11:07.
 */
public class UiView<T extends View> implements UiMode<T> {

    protected int attrIdBackground; // android:background
    protected int attrIdForeground; // android:foreground
    protected int attrIdAlpha;      // android:alpha

    // 动态数据值容器
    protected static TypedValue sOutValue = new TypedValue();

    public static final int NO_ATTR_ID = -1;

    @CallSuper
    @Override
    public void assign(SparseArray<Integer> attrs) {
        if (attrs != null) {
            attrIdBackground = attrs.get(R.styleable.SupportUiMode_android_background, NO_ATTR_ID);
            attrIdForeground = attrs.get(R.styleable.SupportUiMode_android_foreground, NO_ATTR_ID);
            attrIdAlpha = attrs.get(R.styleable.SupportUiMode_android_alpha, NO_ATTR_ID);
        }
    }


    @CallSuper
    @Override
    public <V extends T> void apply(V v, Theme theme) {
        if (v == null || theme == null) return;

        if (CheckUtils.residValid(attrIdBackground)) {
            v.setBackgroundResource(fetchResId(attrIdBackground, theme));
        }

        if (CheckUtils.residValid(attrIdAlpha)) {
            v.setAlpha(fetchFloat(attrIdAlpha, theme));
        }

        if (CheckUtils.residValid(attrIdForeground)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                v.setForeground(Utils.getDrawableCompat(v.getContext(),
                        fetchResId(attrIdForeground, theme)));
            }
        }

    }

    /**
     * 获取具体的ResourceId
     *
     * @param attrId attrId
     * @param theme  当前Activity theme
     * @return 返回具体ResourceId
     */
    public static int fetchResId(@AttrRes int attrId, Theme theme) {
        if (theme == null) {
            return NO_ATTR_ID;
        }
        theme.resolveAttribute(attrId, sOutValue, true);
        return sOutValue.resourceId;
    }

    public static float fetchFloat(@AttrRes int attrId, Theme theme) {
        if (theme == null) {
            return 1.0f;
        }
        theme.resolveAttribute(attrId, sOutValue, true);
        if (sOutValue.type == TypedValue.TYPE_FLOAT) {
            return sOutValue.getFloat();
        }
        return 1.0f;
    }


}
