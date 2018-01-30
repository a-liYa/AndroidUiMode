package com.aliya.uimode.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.aliya.uimode.intef.UiModeChangeListener;

/**
 * 遮罩ImageView
 * <ul>
 * <li> 一、实现圆角功能 <li/>
 * <li> 二、实现固定宽高比功能 <li/>
 * <li>
 *      三、实现夜间模式
 *          1、先获取app:iv_maskColor=""
 *          2、再获取style <item name="iv_maskColor"></item>
 *          3、最后获取 R.color.uiMode_maskColor
 * <li/>
 * <ul/>
 * 通过调用invalidate()刷新日夜模式
 *
 * @author a_liYa
 * @date 16/11/4 21:05.
 */
public class MaskImageView extends AppCompatImageView implements UiModeChangeListener {

    private MaskHelper mMaskHelper;
    private RatioHelper mRatioHelper;
    private RoundedHelper mRoundedHelper;

    public MaskImageView(Context context) {
        this(context, null);
    }

    public MaskImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMaskHelper = new MaskHelper(context, attrs);
        mRatioHelper = new RatioHelper(context, attrs);
        mRoundedHelper = new RoundedHelper(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(
                mRatioHelper.widthMeasureSpec(widthMeasureSpec, heightMeasureSpec,
                        getLayoutParams()),
                mRatioHelper.heightMeasureSpec(widthMeasureSpec, heightMeasureSpec,
                        getLayoutParams()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        setLayerType(LAYER_TYPE_SOFTWARE, null); // 关闭硬件加速
        if (mMaskHelper.validMaskColor() || mRoundedHelper.validRadius()) {
            int saveCount = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(),
                    null, Canvas.ALL_SAVE_FLAG);
            super.onDraw(canvas);

            mMaskHelper.drawMaskColor(canvas);  // 处理遮罩
            mRoundedHelper.drawRounded(canvas, this);  // 处理圆角

            canvas.restoreToCount(saveCount);
        } else {
            super.onDraw(canvas);
        }
    }

    @Override
    public void onUiModeChange() {
        if (getDrawable() != null) {
            mMaskHelper.resolveRealMaskColor();
            invalidate();
        }
    }

    /**
     * 此方法不合理，已过时
     * 设置遮罩颜色
     *
     * @param color mask color
     */
    @Deprecated
    public void setApplyMaskColor(int color) {
//        mApplyMaskColor = color;
//        mPaint.setColor(mApplyMaskColor);
        invalidate();
    }

}
