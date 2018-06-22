package com.aliya.uimode.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.aliya.uimode.R;
import com.aliya.uimode.mode.UiMode;

/**
 * ImageView遮罩 - 助手
 *
 * @author a_liYa
 * @date 2018/1/30 16:22.
 */
class MaskHelper {

    private PorterDuffXfermode xfermodeMask;

    // 从属性中获取的 attr id; 来自 ?attr/
    private int mMaskAttrId = UiMode.NO_ID;
    // 从属性中获取的 color res id; 来自 @color/
    private int mMaskColorResId = UiMode.NO_ID;
    // 从属性中获取的 color; 来自 #xxxxxx
    private Integer mMaskColorHex = null;

    // true:遮罩层与原始图片取交集; false:取并集
    private boolean maskUnion;

    private Context mContext;
    // 实际需要应用的 color
    private int mApplyMaskColor = Color.TRANSPARENT;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static TypedValue sOutValue = new TypedValue();
    /**
     * @see R.attr#maskColor
     */
    private static final String NAME_ATTR_MASK_COLOR = "maskColor"; // MaskImageView 属性名称

    // 属性值
    private static final int DEFAULT_MASK_COLOR_ATTR_ID = R.attr.maskColor;


    public MaskHelper(@NonNull Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaskImageView);
            maskUnion = a.getBoolean(R.styleable.MaskImageView_maskUnion, false);
            a.recycle();

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
                    } else if (!TextUtils.isEmpty(attrVal) && attrVal.startsWith("@")) {
                        String subStr = attrVal.substring(1, attrVal.length());
                        try {
                            mMaskColorResId = Integer.valueOf(subStr);
                        } catch (Exception e) {
                            // no-op
                        }
                    } else { // "#000000"
                        parseHexMaskColor(attrs);
                    }
                    break;
                }
            }
        }

        resolveRealMaskColor();
    }

    public void drawMaskColor(Canvas canvas) {
        if (validMaskColor()) {
            mPaint.setXfermode(getXfermodeMask());
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
        }
    }

    private PorterDuffXfermode getXfermodeMask() {
        if (xfermodeMask == null) {
            xfermodeMask = maskUnion ?
                    new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)    // 遮罩层与原始图片取并集
                    :
                    new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);   // 遮罩层与原始图片取交集
        }
        return xfermodeMask;
    }

    public boolean validMaskColor() {
        return mApplyMaskColor != Color.TRANSPARENT;
    }

    public void resolveRealMaskColor() {
        if (mMaskAttrId != UiMode.NO_ID) {
            resolveColorAttribute(mMaskAttrId);
        } else if (mMaskColorResId != UiMode.NO_ID) {
            mApplyMaskColor = ContextCompat.getColor(mContext, mMaskColorResId);
        } else if (mMaskColorHex != null) {
            mApplyMaskColor = mMaskColorHex; // 先赋值
            Resources.Theme theme = mContext.getTheme();
            if (theme != null &&
                    theme.resolveAttribute(R.attr.iv_useMaskColor, sOutValue, true)) {
                if (sOutValue.type == TypedValue.TYPE_INT_BOOLEAN) {
                    if (sOutValue.data == 0) { // data为0:表示false.
                        // 当前theme配置属性 iv_useMaskColor = false
                        mApplyMaskColor = Color.TRANSPARENT;
                    } else {
                        mApplyMaskColor = mMaskColorHex;
                    }
                }
            }
        } else {
            resolveColorAttribute(DEFAULT_MASK_COLOR_ATTR_ID);
        }

        mPaint.setColor(mApplyMaskColor);
    }

    private void resolveColorAttribute(int attrId) {
        Resources.Theme theme = mContext.getTheme();
        if (theme != null && theme.resolveAttribute(attrId, sOutValue, true)) {
            switch (sOutValue.type) {
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB4:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                    mApplyMaskColor = sOutValue.data;
                    break;
                case TypedValue.TYPE_STRING:
                    mApplyMaskColor = ContextCompat.getColor(mContext, sOutValue.resourceId);
                    break;
                default:
                    mApplyMaskColor = Color.TRANSPARENT;
                    break;
            }
        } else {
            mApplyMaskColor = ContextCompat.getColor(mContext, R.color.uiMode_maskColor);
        }
    }

    private void parseHexMaskColor(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, new int[]{R.attr.maskColor});
        if (a.hasValue(0)) {
            mMaskColorHex = a.getColor(0, Color.TRANSPARENT);
        }
        a.recycle();
    }

}
