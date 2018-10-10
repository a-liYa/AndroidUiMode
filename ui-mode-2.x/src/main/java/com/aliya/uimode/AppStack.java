package com.aliya.uimode;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity栈集合
 *
 * @author a_liYa
 * @date 2017/6/28 15:58.
 */
final class AppStack {

    private static Stack<Activity> sStack;

    public static void pushActivity(Activity activity) {
        if (activity == null) return;

        if (sStack == null) {
            createStack();
        }

        sStack.push(activity);
    }

    public static void removeActivity(Activity activity) {
        if (sStack != null && activity != null) {
            sStack.remove(activity);
        }
    }

    public static Stack<Activity> getAppStack() {
        return sStack;
    }

    private static void createStack() {
        synchronized (AppStack.class) {
            if (sStack == null) {
                sStack = new Stack<>();
            }
        }
    }

}
