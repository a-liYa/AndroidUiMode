package com.aliya.uimode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.aliya.uimode.R;
import com.aliya.uimode.mode.UiMode;

/**
 * 实现圆角 - 助手
 *
 * @author a_liYa
 * @date 2018/1/30 14:31.
 */
class RoundHelper {

    private RectF mRect;
    private Path mClipPath;
    private Path mOverallPath;

    // 圆角半径
    private float radiusLeftTop;
    private float radiusLeftBottom;
    private float radiusRightTop;
    private float radiusRightBottom;
    private boolean radiusOval; // 是否为椭圆
    private float[] radii = new float[8];   // left-top, top-right, bottom-right, bottom-left

    private int borderColor;
    private float borderWidth;
    // private boolean borderOverlay; // 开发者可通过设置 Padding 达到同样效果
    private int borderColorRes = UiMode.NO_ID;

    private Context mContext;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Xfermode PDX_MODE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            ? new PorterDuffXfermode(PorterDuff.Mode.CLEAR) : new PorterDuffXfermode(PorterDuff
            .Mode.DST_IN);

    private static final String NAME_ATTR_BORDER_COLOR = "border_color"; // MaskImageView 属性名称

    public RoundHelper(@NonNull Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Round);
            radiusOval = a.getBoolean(R.styleable.Round_radius_oval, false);

            if (!radiusOval) {
                if (a.hasValue(R.styleable.Round_radius)) {
                    float r = a.getDimension(R.styleable.Round_radius, 0);
                    if (r > 0) {
                        radiusLeftTop = radiusLeftBottom = radiusRightTop = radiusRightBottom = r;
                    }
                } else {
                    radiusLeftTop = getValue(a, R.styleable.Round_radius_leftTop);
                    radiusRightTop = getValue(a, R.styleable.Round_radius_rightTop);
                    radiusRightBottom = getValue(a, R.styleable.Round_radius_rightBottom);
                    radiusLeftBottom = getValue(a, R.styleable.Round_radius_leftBottom);
                }
            }

//            borderOverlay = a.getBoolean(R.styleable.Round_border_overlay, false);
            borderWidth = a.getDimension(R.styleable.Round_border_width, 0);
            borderColor = a.getColor(R.styleable.Round_border_color, Color.TRANSPARENT);
            a.recycle();

            final int N = attrs.getAttributeCount();
            for (int i = 0; i < N; i++) {
                String attrName = attrs.getAttributeName(i);
                if (NAME_ATTR_BORDER_COLOR.equals(attrName)) {
                    String attrVal = attrs.getAttributeValue(i);
                    if (!TextUtils.isEmpty(attrVal) && attrVal.startsWith("@")) {
                        String subStr = attrVal.substring(1);
                        try {
                            borderColorRes = Integer.valueOf(subStr);
                        } catch (Exception e) {
                            // no-op
                        }
                    }
                    break;
                }
            }
        }
    }

    private float getValue(TypedArray a, @StyleableRes int index) {
        return Math.max(a.getDimension(index, 0), 0);
    }

    /**
     * 校验是否需要绘画
     *
     * @return true:需要
     */
    public boolean validNeedDraw() {
        return radiusOval ||
                radiusLeftTop > 0 ||
                radiusLeftBottom > 0 ||
                radiusRightTop > 0 ||
                radiusRightBottom > 0 ||
                (borderWidth > 0 && borderColor != Color.TRANSPARENT);
    }

    public void onDraw(@NonNull Canvas canvas, @NonNull View v) {
        if (!validNeedDraw()) return;

        if (mRect == null) mRect = new RectF();
        if (mClipPath == null) mClipPath = new Path();

        // 处理圆角
        {
            mClipPath.reset();
            mRect.left = v.getPaddingLeft();
            mRect.top = v.getPaddingTop();
            mRect.right = v.getWidth() - v.getPaddingRight();
            mRect.bottom = v.getHeight() - v.getPaddingBottom();
            if (radiusOval) {
                mClipPath.addOval(mRect, Path.Direction.CW);
            } else {
                radii[0] = radii[1] = radiusLeftTop;
                radii[2] = radii[3] = radiusRightTop;
                radii[4] = radii[5] = radiusRightBottom;
                radii[6] = radii[7] = radiusLeftBottom;
                mClipPath.addRoundRect(mRect, radii, Path.Direction.CW);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (mOverallPath == null) mOverallPath = new Path();
                else mOverallPath.reset();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    // 范围上、下、左、右均扩大 1 是因执行缩放动画时边缘偶现1px未被清除.
                    mOverallPath.addRect(-1, -1,
                            canvas.getWidth() + 1, canvas.getHeight() + 1, Path.Direction.CW);
                } else {
                    mOverallPath.addRect(0, 0,
                            canvas.getWidth(), canvas.getHeight(), Path.Direction.CW);
                }
                mClipPath.op(mOverallPath, Path.Op.XOR);
            }

            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setXfermode(PDX_MODE);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(mClipPath, mPaint);
        }

        // 处理边框
        if (borderWidth > 0 && borderColor != Color.TRANSPARENT) {
            mClipPath.reset();
            mRect.left = mRect.left - v.getPaddingLeft() + borderWidth / 2;
            mRect.top = mRect.top - v.getPaddingTop() + borderWidth / 2;
            mRect.right = mRect.right + v.getPaddingRight() - borderWidth / 2;
            mRect.bottom = mRect.bottom + v.getPaddingBottom() - borderWidth / 2;

            if (radiusOval) {
                mClipPath.addOval(mRect, Path.Direction.CW);
            } else {
                radii[0] = radii[1] = Math.max(radiusLeftTop - borderWidth / 2, 0);
                radii[2] = radii[3] = Math.max(radiusRightTop - borderWidth / 2, 0);
                radii[4] = radii[5] = Math.max(radiusRightBottom - borderWidth / 2, 0);
                radii[6] = radii[7] = Math.max(radiusLeftBottom - borderWidth / 2, 0);
                mClipPath.addRoundRect(mRect, radii, Path.Direction.CW);
            }
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(borderWidth);
            mPaint.setColor(borderColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);
        }
    }

    public void refreshBorderColor() {
        if (borderColorRes != UiMode.NO_ID) {
            try {
                borderColor = ContextCompat.getColor(mContext, borderColorRes);
            } catch (Exception e) {
                // no-op
            }
        }
    }

}
