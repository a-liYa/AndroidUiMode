package com.aliya.uimode.demo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aliya.uimode.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 测试兼容性 页面
 *
 * @author a_liYa
 * @date 2017/6/23 下午12:50.
 */
public class TestCompatActivity extends BaseActivity {

    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.btn_left)
    Button mBtnLeft;
    @BindView(R.id.btn_right)
    Button mBtnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_compat);
        ButterKnife.bind(this);


        Resources.Theme theme = getTheme();

        TypedValue value = new TypedValue();
//        ContextThemeWrapper
        theme.resolveAttribute(R.attr.module_app_theme, value, true);

        Log.e("TAG", "value " + value.type);

        Log.e("TAG", value.resourceId + " - " + R.style.ModuleAppTheme + " - " + R.style.ModuleAppThemeNight);


    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_left:


                Log.e("TAG", "");

                break;
            case R.id.btn_right:
                break;
        }
    }

}
