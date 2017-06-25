package com.a_liya.uimode;

import android.content.Context;

/**
 * UiMode管理类
 *
 * @author a_liYa
 * @date 2017/6/23 10:51.
 */
public class UiModeManager {

    private static Context sContext;

    /**
     * 初始化： 只为持有一个ApplicationContext的引用
     *
     * @param context
     */
    public static final void init(Context context) {
        sContext = context.getApplicationContext();
    }

}
