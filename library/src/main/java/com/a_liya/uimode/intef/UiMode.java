package com.a_liya.uimode.intef;

import android.content.res.Resources.Theme;
import android.view.View;

import java.util.Map;

/**
 * 针对View适配日、夜间模式接口
 * <br/>
 * 具体View对应具体{@link UiMode}的实现类
 *
 * @author a_liYa
 * @date 2017/6/23 10:58.
 */
public interface UiMode {

    /**
     * 保存UiMode的属性集
     *
     * @param attrs 属性Map集合
     */
    void saveAttrs(Map<String, Integer> attrs);

    /**
     * 应用对应主题资源
     *
     * @param v      被执行的View
     * @param theme  当前Activity主题
     * @param policy 获取UiApply{@link UiApply}策略
     */
    void apply(View v, Theme theme, ApplyPolicy policy);

}
