package com.a_liya.uimode.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.a_liya.uimode.demo.base.BaseActivity;

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

    @BindView(R.id.view)
    View mView;
    @BindView(R.id.et_num)
    EditText mEtNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_compat);
        ButterKnife.bind(this);

//        Log.e("TAG", "resolve " + b);
//        Log.e("TAG", "resourceId " + outValue.resourceId);
//        Log.e("TAG", "string " + outValue.string);
//        Log.e("TAG", "data " + outValue.data);
//        Log.e("TAG", "getFloat " + outValue.getFloat());
//        Log.e("TAG", "type " + outValue.type);

        Log.e("TAG", "\n ---- \n");

    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        String s = mEtNum.getText().toString();
        long num = Long.parseLong(s);
        long startMs;
        switch (view.getId()) {
            case R.id.btn_left:
//                setTheme(R.style.AppTheme);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                    mView.setBackground(ContextCompat.getDrawable(this, R.attr.bg_view));
//                } else {
////                    mView.setBackgroundDrawable(Utils.getDrawable(this, R.attr.bg_view));
//                }

                break;
            case R.id.btn_right:

//                setTheme(R.style.NightAppTheme);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                    mView.setBackground(Utils.getDrawable(this, R.attr.bg_view));
//                } else {
////                    mView.setBackgroundDrawable(Utils.getDrawable(this, R.attr.bg_view));
//                }
                break;
        }
    }

}
