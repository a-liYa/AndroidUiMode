package com.aliya.uimode.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aliya.uimode.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 测试兼容性 页面 (此页面存在内存泄漏)
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


        ContextThemeWrapper contextWrapper = (ContextThemeWrapper) mEtNum.getContext();

        Log.e("TAG", "context " + contextWrapper);

        while (!(contextWrapper instanceof Activity)) {
            contextWrapper = (ContextThemeWrapper) contextWrapper.getBaseContext();
            Log.e("TAG", "while " + contextWrapper);

        }

        Log.e("TAG", "结束 " + contextWrapper);
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
