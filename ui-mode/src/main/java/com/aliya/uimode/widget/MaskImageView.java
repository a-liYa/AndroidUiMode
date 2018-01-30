package com.aliya.uimode.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
 * <ul>
 * <li>
 *      一、实现圆角功能
 * <li/>
 * <li>
 *      二、实现固定宽高比功能
 * <li/>
 * <li>
 *      三、实现夜间模式
 * <li/>
 * <ul/>
 * <p>
 * 通过调用invalidate()刷新日夜模式
 *
 * @author a_liYa
 * @date 16/11/4 21:05.
 */
public class MaskImageView extends AppCompatImageView implements UiModeChangeListener {

    // PorterDuff.Mode.SRC_ATOP 只在图片有颜色的像素加半透明黑色遮罩
    private PorterDuffXfermode xfermodeMask = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // 从属性中获取的 attr id； 来自?attr/
    private int mMaskAttrId = UiMode.NO_ID;
    // 从属性中获取的 color； 来自@color/
    private Integer mMaskColor = null;
    // 实际需要应用的 color
    private int mApplyMaskColor = NO_COLOR;

    private RatioHelper mRatioHelper;
    private RoundedHelper mRoundedHelper;

    private static TypedValue sOutValue = new TypedValue();
    public static final int NO_COLOR = Color.TRANSPARENT;
    /**
     * @see R.attr#iv_maskColor
     */
    public static String NAME_ATTR_MASK_COLOR = "iv_maskColor"; // MaskImageView 属性名称

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
        mRatioHelper = new RatioHelper(context, attrs);
        mRoundedHelper = new RoundedHelper(context, attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
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
                            // no-op
                        }
                    } else {
                        parseAttrMaskColor(attrs);
                    }
                    break;
                }
            }
        }

        resolveRealMaskColor();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(
                mRatioHelper.widthMeasureSpec(widthMeasureSpec, heightMeasureSpec,
                        getLayoutParams()),
                mRatioHelper.heightMeasureSpec(widthMeasureSpec, heightMeasureSpec,
                        getLayoutParams()));
    }

    private void resolveRealMaskColor() {
        if (mMaskAttrId != UiMode.NO_ID) {
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
        } else {

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
        if (mApplyMaskColor != NO_COLOR || mRoundedHelper.validRadius()) {
            int saveCount = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(),
                    null, Canvas.ALL_SAVE_FLAG);
            super.onDraw(canvas);
            if (mApplyMaskColor != NO_COLOR) { // 处理遮罩
                mPaint.setXfermode(xfermodeMask);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
            }
            if (mRoundedHelper.validRadius()) { // 处理圆角
                mRoundedHelper.drawRounded(canvas, this);
            }
            canvas.restoreToCount(saveCount);
        } else {
            super.onDraw(canvas);
        }
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
