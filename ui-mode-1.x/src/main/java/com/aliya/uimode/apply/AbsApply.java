package com.aliya.uimode.apply;

import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.view.View;

import com.aliya.uimode.intef.UiApply;
import com.aliya.uimode.mode.UiMode;

/**
 * 实现UiApply接口的抽象类，实现公共方法
 * <p>
 * 实现接口UiApply类可改成直接继承AbsApply {@link AbsApply}
 *
 * @author a_liYa
 * @date 2017/6/26 13:15.
 */
public abstract class AbsApply implements UiApply {

    /**
     * 校验 参数 是否全部合法
     *
     * @param v      a view
     * @param attrId attr id
     * @return true ：参数合法
     */
    protected static boolean argsValid(View v, @AttrRes int attrId) {
        return v != null && UiMode.attrIdValid(attrId)
                && v.getContext() != null && getTheme(v) != null;
    }

    /**
     * 从 view 获取 theme
     *
     * @param v a view
     * @return theme
     */
    protected static Resources.Theme getTheme(View v) {
        return v.getContext().getTheme();
    }

}
