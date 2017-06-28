package com.aliya.uimode.demo;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.aliya.uimode.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试效率 - 页面
 *
 * @author a_liYa
 * @date 2017/6/27 上午9:06.
 */
public class TestEfficientActivity extends BaseActivity {

    @BindView(R.id.v1)
    View mV1;
    @BindView(R.id.v2)
    View mV2;
    @BindView(R.id.v3)
    View mV3;
    @BindView(R.id.v4)
    View mV4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_efficient);
        ButterKnife.bind(this);
        TestUtils.add(this, mV1);
        TestUtils.add(this, mV2);
        TestUtils.add(this, mV3);
        TestUtils.add(this, mV4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy time: " + SystemClock.uptimeMillis());
        TestUtils.print();
        TestUtils.post();
    }

    private void testNew(long num) {
        long startMs;
        startMs = SystemClock.uptimeMillis();

        for (int i = 0; i < num; i++) {
            SparseArray<Integer> attrArrays = new SparseArray<>();
            attrArrays.put(i, new Integer(i));
            attrArrays.put(i + 1, new Integer(i));
            attrArrays.put(i + 2, new Integer(i));
//                    sAttrArrays.clear();
        }
        Log.e("TAG", "创建 次数: " + num + "; 时长：" + (SystemClock.uptimeMillis() - startMs));
    }

    private void testReuse(long num) {
        long startMs;
        startMs = SystemClock.uptimeMillis();
        SparseArray<Integer> sAttrArrays = new SparseArray<>();

        for (int i = 0; i < num; i++) {
            sAttrArrays.put(i, new Integer(i));
            sAttrArrays.put(i + 1, new Integer(i));
            sAttrArrays.put(i + 2, new Integer(i));
            sAttrArrays.clear();
        }
        Log.e("TAG", "复用 次数: " + num + "; 时长：" + (SystemClock.uptimeMillis() - startMs));
    }

}
