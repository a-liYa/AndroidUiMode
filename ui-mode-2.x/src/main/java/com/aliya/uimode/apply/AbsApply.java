package com.aliya.uimode.apply;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.aliya.uimode.intef.UiApply;
import com.aliya.uimode.mode.ResourceEntry;
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

    protected boolean applyAttr(View v, ResourceEntry entry) {
        return false;
    }

    /**
     * 校验 参数 是否全部合法
     *
     * @param v     a view
     * @param entry 资源实体类
     * @return true ：参数合法
     */
    protected static boolean validArgs(View v, ResourceEntry entry) {
        return v != null && entry != null &&
                UiMode.idValid(entry.getId()) && !TextUtils.isEmpty(entry.getType());
    }

    /**
     * 校验 Theme 是否为null
     *
     * @param v a view
     * @return true : 不为null
     */
    protected static boolean validTheme(View v) {
        return getTheme(v) != null;
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

    /**
     * 检索主题中属性的值
     *
     * @param v           a view.
     * @param resId       The resource identifier of the desired theme attribute.
     * @return boolean Returns true if the attribute was found and <var>outValue</var>
     * is valid, else false.
     * @see android.content.res.Resources.Theme#resolveAttribute(int, TypedValue, boolean)
     */
    protected static boolean resolveAttribute(View v, int resId) {
        return getTheme(v).resolveAttribute(resId, sOutValue, true);
    }

}
