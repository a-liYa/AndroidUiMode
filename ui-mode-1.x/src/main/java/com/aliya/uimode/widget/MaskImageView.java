package com.aliya.uimode.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.aliya.uimode.R;
import com.aliya.uimode.intef.UiModeChangeListener;
import com.aliya.uimode.mode.UiMode;

/**
 * 遮罩ImageView
 * <p>
 * 通过调用invalidate()刷新日夜模式
 *
 * @author a_liYa
 * @date 16/11/4 21:05.
 */
public class MaskImageView extends AppCompatImageView implements UiModeChangeListener {

    // PorterDuff.Mode.SRC_ATOP 只在图片有颜色的像素加半透明黑色遮罩
    private PorterDuffXfermode xfermodeMask = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    // 处理圆角
    private PorterDuffXfermode xfermodeRadius = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * @see R.attr#iv_maskColor
     */
    private static final String NAME_ATTR_MASK_COLOR = "iv_maskColor"; // MaskImageView 属性名称

    private int mMaskAttrId = UiMode.NO_ATTR_ID;
    private Integer mMaskColor = null;
    private int mApplyMaskColor = NO_COLOR;

    private float radius;
    private RectF mRect;
    private Path mPath;

    private RatioHelper helper;

    private static TypedValue sOutValue = new TypedValue();
    private static final int NO_COLOR = Color.TRANSPARENT;

    // 属性值
    private static final int DEFAULT_MASK_COLOR_ATTR_ID = R.attr.iv_maskColor;

    public MaskImageView(Context context) {
        this(context, null);
    }

    public MaskImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        helper = new RatioHelper(context, attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            if (!TextUtils.isEmpty(NAME_ATTR_MASK_COLOR)) {
                final int N = attrs.getAttributeCount();
                for (int i = 0; i < N; i++) {
                    String attrName = attrs.getAttributeName(i);
                    if (NAME_ATTR_MASK_COLOR.equals(attrName)) {
                        String attrVal = attrs.getAttributeValue(i);
                        if (!TextUtils.isEmpty(attrVal) && attrVal.startsWith("?")) {
                            String subStr = attrVal.substring(1, attrVal.length());
                            try {
                                mMaskAttrId = Integer.valueOf(subStr);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            parseAttrMaskColor(attrs);
                        }
                        break;
                    }
                }
            }

            TypedArray a = getContext().obtainStyledAttributes(attrs, new int[]{R.attr.radius});
            if (a.hasValue(0)) {
                radius = a.getDimension(0, 0);
            }
            a.recycle();
        }

        resolveRealMaskColor();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(
                helper.widthMeasureSpec(widthMeasureSpec, heightMeasureSpec, getLayoutParams()),
                helper.heightMeasureSpec(widthMeasureSpec, heightMeasureSpec, getLayoutParams()));
    }

    private void resolveRealMaskColor() {
        if (mMaskAttrId != UiMode.NO_ATTR_ID) {
            resolveColorAttribute(mMaskAttrId);
        } else {
            if (mMaskColor != null) {
                Resources.Theme theme = getContext().getTheme();
                if (theme != null &&
                        theme.resolveAttribute(R.attr.iv_useMaskColor, sOutValue, true)) {
                    if (sOutValue.type == TypedValue.TYPE_INT_BOOLEAN) {
                        if (sOutValue.data == 0) { // data为0:表示false
                            mApplyMaskColor = NO_COLOR;
                        } else {
                            mApplyMaskColor = mMaskColor;
                        }
                    }
                }
            } else {
                resolveColorAttribute(DEFAULT_MASK_COLOR_ATTR_ID);
            }
        }

        mPaint.setColor(mApplyMaskColor);
    }

    private void resolveColorAttribute(int attrId) {
        Resources.Theme theme = getContext().getTheme();
        if (theme != null && theme.resolveAttribute(attrId, sOutValue, true)) {
            switch (sOutValue.type) {
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB4:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                    mApplyMaskColor = sOutValue.data;
                    break;
                case TypedValue.TYPE_STRING:
                    mApplyMaskColor = ContextCompat.getColor(getContext(), sOutValue.resourceId);
                    break;
                default:
                    mApplyMaskColor = NO_COLOR;
                    break;
            }
        }
    }

    private void parseAttrMaskColor(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, new int[]{R.attr.iv_maskColor});
        if (a.hasValue(0)) {
            mMaskColor = a.getColor(0, NO_COLOR);
        }
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        setLayerType(LAYER_TYPE_SOFTWARE, null); // 关闭硬件加速
        if (mApplyMaskColor != NO_COLOR || radius > 0) {
            int saveCount = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(),
                    null, Canvas.ALL_SAVE_FLAG);
            super.onDraw(canvas);
            if (mApplyMaskColor != NO_COLOR) { // 处理遮罩
                mPaint.setXfermode(xfermodeMask);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
            }
            if (radius > 0) { // 处理圆角
                mPaint.setXfermode(xfermodeRadius);
                drawLeftTop(canvas, radius);
                drawRightTop(canvas, radius);
                drawRightBottom(canvas, radius);
                drawLeftBottom(canvas, radius);
            }
            canvas.restoreToCount(saveCount);
        } else {
            super.onDraw(canvas);
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

    private void drawLeftTop(Canvas canvas, float radius) {
        initPath();
        mPath.moveTo(0, radius);
        mPath.lineTo(0, 0);
        mPath.lineTo(radius, 0);
        mRect.set(0, 0, radius * 2, radius * 2);
        mPath.arcTo(mRect, -90, -90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawLeftBottom(Canvas canvas, float radius) {
        initPath();
        mPath.moveTo(0, getHeight() - radius);
        mPath.lineTo(0, getHeight());
        mPath.lineTo(radius, getHeight());
        mRect.set(0, // x
                getHeight() - radius * 2, // y
                radius * 2, // x
                getHeight());
        mPath.arcTo(mRect, 90, 90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawRightBottom(Canvas canvas, float radius) {
        initPath();
        mPath.moveTo(getWidth() - radius, getHeight());
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(getWidth(), getHeight() - radius);
        mRect.set(getWidth() - radius * 2, getHeight()
                - radius * 2, getWidth(), getHeight());
        mPath.arcTo(mRect, 0, 90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawRightTop(Canvas canvas, float radius) {
        initPath();
        mPath.moveTo(getWidth(), radius);
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(getWidth() - radius, 0);
        mRect.set(getWidth() - radius * 2, 0, getWidth(), 0 + radius * 2);
        mPath.arcTo(mRect, -90, 90);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 圆角、clipPath，有锯齿
     * mRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
     * mPath.addRoundRect(mRect, radius, radius, Path.Direction.CCW);
     * canvas.clipPath(mPath, Region.Op.REPLACE);
     */


    @Override
    public void onUiModeChange() {
        if (getDrawable() != null) {
            resolveRealMaskColor();
            invalidate();
        }
    }

    /**
     * 设置遮罩颜色
     *
     * @param color mask color
     */
    public void setApplyMaskColor(int color) {
        mApplyMaskColor = color;
        mPaint.setColor(mApplyMaskColor);
        invalidate();
    }

}
