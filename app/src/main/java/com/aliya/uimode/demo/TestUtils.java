//package com.aliya.uimode.demo;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.MessageQueue;
//import android.os.SystemClock;
//import android.util.Log;
//import android.view.View;
//
//import java.lang.ref.ReferenceQueue;
//import java.lang.ref.WeakReference;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 测试工具类
// *
// * @author a_liYa
// * @date 2017/6/27 13:12.
// */
//public final class TestUtils {
//
//    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
//
//    private static Map<Context, Set<WeakReference<View>>> views = new HashMap<>();
//    private static ReferenceQueue<View> queue = new ReferenceQueue<>();
//
//    private static final Runnable waitRunnable = new Runnable() {
//        @Override
//        public void run() {
//            waitForIdle();
//        }
//    };
//
//    private static final Runnable clearRunnable = new Runnable() {
//        @Override
//        public void run() {
//            waitForIdleClear();
//        }
//    };
//
//    public static void post() {
//        mainHandler.post(waitRunnable);
//    }
//
//    private static void waitForIdle() {
//        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
//            @Override
//            public boolean queueIdle() {
//                Log.e("TAG", "waitForIdle queueIdle time:" + SystemClock.uptimeMillis());
//                print();
//                mainHandler.postDelayed(clearRunnable, 5000);
//                return false;
//            }
//        });
//    }
//
//    private static void waitForIdleClear() {
//        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
//            @Override
//            public boolean queueIdle() {
//                Log.e("TAG", "waitForIdleClear queueIdle time:" + SystemClock.uptimeMillis());
//                print();
//                return false;
//            }
//        });
//    }
//
//    public static void add(Context key, View view) {
//        key = key.getApplicationContext();
//        Set<WeakReference<View>> sets = views.get(key);
//        if (sets == null) {
//            sets = new HashSet<>();
//            views.put(key, sets);
//        }
//        sets.add(new WeakReference<>(view, queue));
//    }
//
//    public static void print() {
//        Log.e("TAG", "打印 ----------- start");
//        for (Map.Entry<Context, Set<WeakReference<View>>> entry : views.entrySet()) {
//            Log.e("TAG", "--- " + entry.getKey());
//            Set<WeakReference<View>> value = entry.getValue();
//            for (WeakReference<View> weak : value) {
//                View view = weak.get();
//                if (view != null) {
//                    Log.e("TAG", "" + view);
//                } else {
//                    Log.e("TAG", "已经回收 " + weak);
//                }
//            }
//        }
//
//        Log.e("TAG", "遍历回收 ");
//        WeakReference<View> ref;
//
//        while ((ref = (WeakReference) queue.poll()) != null) {
//            Log.e("TAG", "回收 " + ref);
//        }
//
//        Log.e("TAG", "打印 ----------- end");
//
//    }
//
//
//}
