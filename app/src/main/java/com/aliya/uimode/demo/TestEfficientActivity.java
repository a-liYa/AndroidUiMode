package com.aliya.uimode.demo;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;

import com.aliya.uimode.demo.base.BaseActivity;

/**
 * 测试效率 - 页面
 *
 * @author a_liYa
 * @date 2017/6/27 上午9:06.
 */
public class TestEfficientActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_efficient);
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
