package com.aliya.uimode.demo;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 测试工具类
 *
 * @author a_liYa
 * @date 2017/6/27 13:12.
 */
public final class TestUtils {

    private static Map<Context, Set<WeakReference<View>>> views = new HashMap<>();
    private static ReferenceQueue<View> queue = new ReferenceQueue<>();

    public static void add(Context key, View view) {
        key = key.getApplicationContext();
        Set<WeakReference<View>> sets = views.get(key);
        if (sets == null) {
            sets = new HashSet<>();
            views.put(key, sets);
        }
        sets.add(new WeakReference<>(view, queue));
    }

    public static void print() {
        Log.e("TAG", "打印 ----------- start");
        for (Map.Entry<Context, Set<WeakReference<View>>> entry : views.entrySet()) {
            Log.e("TAG", "--- " + entry.getKey());
            Set<WeakReference<View>> value = entry.getValue();
            for (WeakReference<View> weak : value) {
                View view = weak.get();
                if (view != null) {
                    Log.e("TAG", "" + view);
                } else {
                    Log.e("TAG", "已经回收 " + weak);
                }
            }
        }

        Log.e("TAG", "遍历回收 ");
        WeakReference<View> ref;

        while ((ref = (WeakReference)queue.poll()) != null) {
            Log.e("TAG", "回收 " + ref);
        }

        Log.e("TAG", "打印 ----------- end");

    }


}
