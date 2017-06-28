package com.aliya.uimode.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.aliya.uimode.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CycleActivity extends BaseActivity {

    @BindView(R.id.btn_cycle)
    Button mBtnCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_cycle)
    public void onViewClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
