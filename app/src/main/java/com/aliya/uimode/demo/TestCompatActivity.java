package com.aliya.uimode.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                LayoutInflater from = LayoutInflater.from(this);

                Log.e("TAG", "Activity " + from);

//                TestUtils.print();
                break;
            case R.id.btn_right:
//                LayoutInflater appFrom = LayoutInflater.from(getApplicationContext());

//                Log.e("TAG", "AppContext " + appFrom);
                break;
        }
    }

}
