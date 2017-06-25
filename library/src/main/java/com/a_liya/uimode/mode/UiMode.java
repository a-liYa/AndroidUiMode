package com.a_liya.uimode.mode;

import android.content.res.Resources.Theme;
import android.util.SparseArray;
import android.view.View;

/**
 * 针对View适配日、夜间模式接口
 * <br/>
 * 具体View对应具体{@link UiMode}的实现类
 *
 * @author a_liYa
 * @date 2017/6/23 10:58.
 */
public interface UiMode<T extends View> {

    /**
     * 变量（attrIds）赋值
     *
     * @param attrs 属性值集合
     */
    void assign(SparseArray<Integer> attrs);


    /**
     * 应用对应主题资源
     *
     * @param v     被执行的View
     * @param theme 当前Activity主题
     * @param <V>   v的泛型
     */
    <V extends T> void apply(V v, Theme theme);

}
