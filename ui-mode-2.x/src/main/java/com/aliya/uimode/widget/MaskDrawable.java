package com.aliya.uimode.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.aliya.uimode.intef.UiModeChangeListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 遮罩 Drawable 包装类
 *
 * @author a_liYa
 * @date 2018/11/9 17:16.
 */
public class MaskDrawable extends Drawable implements UiModeChangeListener {

    private Drawable mDrawable;
    private MaskHelper mMaskHelper;

    public MaskDrawable(Drawable drawable, MaskHelper maskHelper) {
        mDrawable = drawable;
        mMaskHelper = maskHelper;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mMaskHelper.validMaskColor()) {
            final int saveCount = canvas.saveLayer(0, 0,
                    canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);

            mDrawable.draw(canvas);
            mMaskHelper.drawMaskColor(canvas);  // 处理遮罩

            canvas.restoreToCount(saveCount);
        } else {
            mDrawable.draw(canvas);
        }
    }

    @Override
    public void onUiModeChange() {
        mMaskHelper.resolveRealMaskColor();
    }

    @Override
    public void setAlpha(int alpha) {
        mDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mDrawable.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return mDrawable.getOpacity();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        mDrawable.setBounds(left, top, right, bottom);
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        mDrawable.setBounds(bounds);
        super.setBounds(bounds);
    }

    @NonNull
    @Override
    public Rect getDirtyBounds() {
        return mDrawable.getBounds();
    }

    @Override
    public void setChangingConfigurations(int configs) {
        mDrawable.setChangingConfigurations(configs);
        super.setChangingConfigurations(configs);
    }

    @Override
    public int getChangingConfigurations() {
        return mDrawable.getChangingConfigurations();
    }

    @Override
    public void setDither(boolean dither) {
        mDrawable.setDither(dither);
        super.setDither(dither);
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mDrawable.setFilterBitmap(filter);
        super.setFilterBitmap(filter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean isFilterBitmap() {
        return mDrawable.isFilterBitmap();
    }

    @Nullable
    @Override
    public Callback getCallback() {
        return mDrawable.getCallback();
    }

    @Override
    public void invalidateSelf() {
        mDrawable.invalidateSelf();
        super.invalidateSelf();
    }

    @Override
    public void scheduleSelf(@NonNull Runnable what, long when) {
        mDrawable.scheduleSelf(what, when);
        super.scheduleSelf(what, when);
    }

    @Override
    public void unscheduleSelf(@NonNull Runnable what) {
        mDrawable.unscheduleSelf(what);
        super.unscheduleSelf(what);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int getLayoutDirection() {
        return mDrawable.getLayoutDirection();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onLayoutDirectionChanged(int layoutDirection) {
        return mDrawable.onLayoutDirectionChanged(layoutDirection);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int getAlpha() {
        return mDrawable.getAlpha();
    }

    @Override
    public void setColorFilter(int color, @NonNull PorterDuff.Mode mode) {
        mDrawable.setColorFilter(color, mode);
        super.setColorFilter(color, mode);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTint(int tintColor) {
        mDrawable.setTint(tintColor);
        super.setTint(tintColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTintList(@Nullable ColorStateList tint) {
        mDrawable.setTintList(tint);
        super.setTintList(tint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
        mDrawable.setTintMode(tintMode);
        super.setTintMode(tintMode);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public ColorFilter getColorFilter() {
        return mDrawable.getColorFilter();
    }

    @Override
    public void clearColorFilter() {
        mDrawable.clearColorFilter();
        super.clearColorFilter();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setHotspot(float x, float y) {
        mDrawable.setHotspot(x, y);
        super.setHotspot(x, y);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        mDrawable.setHotspotBounds(left, top, right, bottom);
        super.setHotspotBounds(left, top, right, bottom);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void getHotspotBounds(@NonNull Rect outRect) {
        mDrawable.getHotspotBounds(outRect);
        super.getHotspotBounds(outRect);
    }

    @Override
    public boolean isStateful() {
        return mDrawable.isStateful();
    }

    @Override
    public boolean setState(@NonNull int[] stateSet) {
        mDrawable.setState(stateSet);
        return super.setState(stateSet);
    }

    @NonNull
    @Override
    public int[] getState() {
        return mDrawable.getState();
    }

    @Override
    public void jumpToCurrentState() {
        mDrawable.jumpToCurrentState();
        super.jumpToCurrentState();
    }

    @NonNull
    @Override
    public Drawable getCurrent() {
        return super.getCurrent();
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        super.setVisible(visible, restart);
        return mDrawable.setVisible(visible, restart);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void setAutoMirrored(boolean mirrored) {
        mDrawable.setAutoMirrored(mirrored);
        super.setAutoMirrored(mirrored);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean isAutoMirrored() {
        return mDrawable.isAutoMirrored();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void applyTheme(@NonNull Resources.Theme t) {
        mDrawable.applyTheme(t);
        super.applyTheme(t);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean canApplyTheme() {
        return mDrawable.canApplyTheme();
    }

    @Nullable
    @Override
    public Region getTransparentRegion() {
        return mDrawable.getTransparentRegion();
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public int getMinimumWidth() {
        return mDrawable.getMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        return mDrawable.getMinimumHeight();
    }

    @Override
    public boolean getPadding(@NonNull Rect padding) {
        super.getPadding(padding);
        return mDrawable.getPadding(padding);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getOutline(@NonNull Outline outline) {
        mDrawable.getOutline(outline);
        super.getOutline(outline);
    }

    @NonNull
    @Override
    public Drawable mutate() {
        mDrawable.mutate();
        return super.mutate();
    }

    @Override
    public void inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull
            AttributeSet attrs) throws XmlPullParserException, IOException {
        mDrawable.inflate(r, parser, attrs);
        super.inflate(r, parser, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull
            AttributeSet attrs, @Nullable Resources.Theme theme) throws XmlPullParserException,
            IOException {
        mDrawable.inflate(r, parser, attrs, theme);
        super.inflate(r, parser, attrs, theme);
    }

    @Nullable
    @Override
    public ConstantState getConstantState() {
        return mDrawable.getConstantState();
    }

}
