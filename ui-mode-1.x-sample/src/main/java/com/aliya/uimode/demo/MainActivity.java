package com.aliya.uimode.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliya.uimode.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(this, TestCompatActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, CycleActivity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(this, TestEfficientActivity.class));
                break;
            case R.id.btn_4:
                startActivity(new Intent(this, UiModeActivity.class));
                break;
            case R.id.btn_5:
                startActivity(new Intent(this, TestThemeActivity.class));
                break;
        }
    }

}
