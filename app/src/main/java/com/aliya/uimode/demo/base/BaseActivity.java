package com.aliya.uimode.demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aliya.uimode.UiModeManager;
import com.aliya.uimode.demo.ThemeMode;

/**
 * BaseActivity
 *
 * @author a_liYa
 * @date 2017/6/26 17:21.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeMode.fitTheme2Activity(this);
        UiModeManager.setInflaterFactor(getLayoutInflater());

//        LayoutInflaterCompat.setFactory(getLayoutInflater(), new LayoutInflaterFactory() {
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet
//                    attrs) {
//                Log.e("TAG", "onCreateView at time " + SystemClock.uptimeMillis());
//                return null;
//            }
//        });

        super.onCreate(savedInstanceState);

    }
}
