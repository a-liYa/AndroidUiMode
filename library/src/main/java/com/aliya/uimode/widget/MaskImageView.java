package com.aliya.uimode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.aliya.uimode.R;

/**
 * 遮罩ImageView
 * <p>
 * 通过调用invalidate()刷新日夜模式
 *
 * @author a_liYa
 * @date 16/11/4 21:05.
 */
public class MaskImageView extends AppCompatImageView {

    // PorterDuff.Mode.SRC_ATOP 只在图片有颜色的像素加半透明黑色遮罩
    PorterDuffXfermode pdx = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    // DARKEN，LIGHTEN, SRC_OVER

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mMaskColor;

    private static final int[] mAttrs = {R.attr.iv_maskColor};

    public MaskImageView(Context context) {
        this(context, null);
    }

    public MaskImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
//            mMaskColor = ContextCompat.getColor(getContext(), R.color.night_mode_mask);
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, mAttrs);
//            mMaskColor = a.getColor(0, ContextCompat.getColor(getContext(), R.color
//                    .night_mode_mask));
            a.recycle();
        }
        mPaint.setColor(mMaskColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        setLayerType(LAYER_TYPE_SOFTWARE, null); // 关闭硬件加速
//        if (UiModeManager.get().isNightMode()) { // 夜间模式走这里
//            int layerId;
//            if (canvas == BlurFrameLayout.sTempCanvas) { // 高斯模糊分发的canvas
//                layerId = canvas.saveLayer(0, 0, canvas.getWidth() / BlurFrameLayout.BITMAP_RATIO,
//                        canvas.getHeight() / BlurFrameLayout.BITMAP_RATIO, null, Canvas
//                                .ALL_SAVE_FLAG);
//            } else {
//                layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null,
// Canvas
//                        .ALL_SAVE_FLAG);
//            }
//            super.onDraw(canvas);
//            mPaint.setXfermode(pdx);
//            if (canvas == BlurFrameLayout.sTempCanvas) {
//                canvas.drawRect(0, 0, canvas.getWidth() / BlurFrameLayout.BITMAP_RATIO,
//                        canvas.getHeight() / BlurFrameLayout.BITMAP_RATIO, mPaint);
//            } else {
//                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
//            }
//            mPaint.setXfermode(null);
//            canvas.restoreToCount(layerId);
//        } else {
        super.onDraw(canvas);
//        }
    }

//    @Override
//    public void onUiModeChange() {
//        if (getDrawable() != null) {
//            invalidate();
//        }
//    }
}
