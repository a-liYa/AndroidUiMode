package com.aliya.uimode.demo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliya.uimode.demo.base.BaseActivity;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.UiMode;
import com.aliya.uimode.widget.MaskImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 夜间模式切换 - 页面
 *
 * @author a_liYa
 * @date 2017/6/27 下午1:00.
 */
public class UiModeActivity extends BaseActivity {

    @BindView(R.id.ll)
    LinearLayout mLl;
    @BindView(R.id.iv_mask)
    MaskImageView mIvMask;
    @BindView(R.id.tv_text)
    TextView mTvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_mode);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_left, R.id.btn_right, R.id.iv_mask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                long start = SystemClock.uptimeMillis();
                ThemeMode.setUiMode(false);
                Log.e("TAG", "模式切换执行的时间 " + (SystemClock.uptimeMillis() - start) + " ms");
                break;
            case R.id.btn_right:
                long startMs = SystemClock.uptimeMillis();
                ThemeMode.setUiMode(true);
                Log.e("TAG", "模式切换执行的时间 " + (SystemClock.uptimeMillis() - startMs) + " ms");
                break;
            case R.id.iv_mask:

                break;
        }
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {

        TextView textView = new TextView(this);
        textView.setText("我是第" + mLl.getChildCount() + "个View");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(this, R.color.tc_3b424c));
        mLl.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, 150);
        UiMode.putUiModeView(textView, Attr.builder().add(Attr.NAME_TC, R.attr.tc_3b424c).build());

    }

}
