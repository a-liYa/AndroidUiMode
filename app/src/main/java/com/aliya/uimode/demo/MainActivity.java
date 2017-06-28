package com.aliya.uimode.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.aliya.uimode.demo.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4})
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
//                startActivity(new Intent(this, UiModeActivity.class));
                break;
        }
    }

}
