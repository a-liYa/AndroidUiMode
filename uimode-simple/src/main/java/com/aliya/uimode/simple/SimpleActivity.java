package com.aliya.uimode.simple;

import android.os.Bundle;

import com.aliya.uimode.simple.base.BaseActivity;

/**
 * 示例页
 *
 * @author a_liYa
 * @date 2018/2/4 下午4:54.
 */
public class SimpleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
    }
}
