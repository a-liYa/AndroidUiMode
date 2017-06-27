package com.a_liya.uimode.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.a_liya.uimode.demo.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(this, TestCompatActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, CycleActivity.class));
                break;
        }
    }

}
