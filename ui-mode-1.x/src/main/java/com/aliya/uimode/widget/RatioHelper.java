package com.aliya.uimode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;

import com.aliya.uimode.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 宽高比 - 助手
 *
 * @author a_liYa
 * @date 2017/7/18 23:00.
 */
class RatioHelper {

    private float ratio_w2h = NO_VALUE;

    private static final String RATIO_SYMBOL = ":";
    static final int NO_VALUE = -1;

    public RatioHelper(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        if (ta.hasValue(R.styleable.RatioLayout_ratio_w2h)) {
            String w_h = ta.getString(R.styleable.RatioLayout_ratio_w2h);
            if (!TextUtils.isEmpty(w_h) && w_h.contains(RATIO_SYMBOL)) {
                String[] split = w_h.trim().split(RATIO_SYMBOL);
                if (split != null && split.length == 2) {
                    try {
                        ratio_w2h = Float.parseFloat(split[0].trim())
                                / Float.parseFloat(split[1].trim());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ta.recycle();
    }

    public float getRatio() {
        return ratio_w2h;
    }

    public RatioHelper setRatio(float w2h) {
        this.ratio_w2h = w2h;
        return this;
    }

    public int widthMeasureSpec(int widthMeasureSpec, int heightMeasureSpec,
                                LayoutParams params) {
        if (ratio_w2h > 0) {
            int wMode = MeasureSpec.getMode(widthMeasureSpec);
            int hMode = MeasureSpec.getMode(heightMeasureSpec);

            int hSize = MeasureSpec.getSize(heightMeasureSpec);

            if (wMode != MeasureSpec.EXACTLY && hMode == MeasureSpec.EXACTLY ||
                    params.width == WRAP_CONTENT && params.height != WRAP_CONTENT) {
                widthMeasureSpec = MeasureSpec
                        .makeMeasureSpec(Math.round(hSize * ratio_w2h), View.MeasureSpec.EXACTLY);
            }
        }
        return widthMeasureSpec;
    }

    public int heightMeasureSpec(int widthMeasureSpec, int heightMeasureSpec,
                                 LayoutParams params) {
        if (ratio_w2h > 0) {
            int wMode = MeasureSpec.getMode(widthMeasureSpec);
            int hMode = MeasureSpec.getMode(heightMeasureSpec);

            int wSize = MeasureSpec.getSize(widthMeasureSpec);

            if (wMode == MeasureSpec.EXACTLY && hMode != MeasureSpec.EXACTLY
                    || params.width != WRAP_CONTENT && params.height == WRAP_CONTENT) {
                heightMeasureSpec = MeasureSpec
                        .makeMeasureSpec(Math.round(wSize / ratio_w2h), MeasureSpec.EXACTLY);
            }
        }
        return heightMeasureSpec;
    }
}
