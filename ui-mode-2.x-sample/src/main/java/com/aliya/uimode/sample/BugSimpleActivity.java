package com.aliya.uimode.sample;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.aliya.uimode.sample.base.BaseActivity;

/**
 * 问题 示例页 Android 9.0
 *
 * @author a_liYa
 * @date 2018/11/14 下午3:14.
 */
public class BugSimpleActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    ImageView mImageView;
    SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_simple);
        mImageView = findViewById(R.id.iv);
        mSeekBar = findViewById(R.id.seek_bar);

        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void changeToImageView(float progress) {

        float scale = 1 - 0.8f * progress;
        mImageView.setScaleX(scale);
        mImageView.setScaleY(scale);
//        //水平移动
//        mImageView.setTranslationX(250 * progress);
//        //垂直移动
//        mImageView.setTranslationY(50 * progress);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        changeToImageView((float) progress / seekBar.getMax());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
