package com.aliya.uimode.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aliya.uimode.simple.base.BaseActivity;

/**
 * 主界面
 *
 * @author a_liYa
 * @date 2018/2/1 下午3:06.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_go_simple).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_simple:
                startActivity(new Intent(this, SimpleActivity.class));
                break;
        }
    }

}
