package com.a_liya.uimode.demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * BaseActivity
 *
 * @author a_liYa
 * @date 2017/6/26 17:21.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        LayoutInflaterCompat.setFactory(getLayoutInflater(), UiModeManager
// .obtainInflaterFactory());
//        Log.e("TAG", "" + getLayoutInflater());
        super.onCreate(savedInstanceState);

    }
}
