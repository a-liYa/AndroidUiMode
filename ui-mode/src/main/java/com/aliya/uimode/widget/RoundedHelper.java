package com.aliya.uimode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.aliya.uimode.R;

/**
 * 实现圆角 - 助手
 *
 * @author a_liYa
 * @date 2018/1/30 14:31.
 */
class RoundedHelper {

    private Path mPath;
    private RectF mRect;
    private float radius;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RoundedHelper(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, new int[]{R.attr.radius});
            if (a.hasValue(0)) {
                radius = a.getDimension(0, 0);
            }
            a.recycle();
        }
        // 处理圆角
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    /**
     * 半径是否有效
     *
     * @return true:有效
     */
    public boolean validRadius() {
        return radius > 0;
    }

    public void drawRounded(@NonNull Canvas canvas, @NonNull View v) {
        if (validRadius()) {
            drawLeftTop(canvas, v, radius);
            drawRightTop(canvas, v, radius);
            drawRightBottom(canvas, v, radius);
            drawLeftBottom(canvas, v, radius);
        }
    }

    private void initPath() {
        if (mPath == null) {
            mPath = new Path();
        } else {
            mPath.reset();
        }
        if (mRect == null) {
            mRect = new RectF();
        }
    }

    private void drawLeftTop(Canvas canvas, View v, float radius) {
        initPath();
        mPath.moveTo(0, radius);
        mPath.lineTo(0, 0);
        mPath.lineTo(radius, 0);
        mRect.set(0, 0, radius * 2, radius * 2);
        mPath.arcTo(mRect, -90, -90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawLeftBottom(Canvas canvas, View v, float radius) {
        initPath();
        mPath.moveTo(0, v.getHeight() - radius);
        mPath.lineTo(0, v.getHeight());
        mPath.lineTo(radius, v.getHeight());
        mRect.set(0, // x
                v.getHeight() - radius * 2, // y
                radius * 2, // x
                v.getHeight());
        mPath.arcTo(mRect, 90, 90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawRightBottom(Canvas canvas, View v, float radius) {
        initPath();
        mPath.moveTo(v.getWidth() - radius, v.getHeight());
        mPath.lineTo(v.getWidth(), v.getHeight());
        mPath.lineTo(v.getWidth(), v.getHeight() - radius);
        mRect.set(v.getWidth() - radius * 2, v.getHeight()
                - radius * 2, v.getWidth(), v.getHeight());
        mPath.arcTo(mRect, 0, 90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawRightTop(Canvas canvas, View v, float radius) {
        initPath();
        mPath.moveTo(v.getWidth(), radius);
        mPath.lineTo(v.getWidth(), 0);
        mPath.lineTo(v.getWidth() - radius, 0);
        mRect.set(v.getWidth() - radius * 2, 0, v.getWidth(), 0 + radius * 2);
        mPath.arcTo(mRect, -90, 90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

}
