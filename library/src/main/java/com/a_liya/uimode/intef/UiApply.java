package com.a_liya.uimode.intef;

import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用对应的UiMode接口
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public interface UiApply {

    TypedValue sOutValue = new TypedValue();

    /**
     * 应用对应主题的资源
     *
     * @param v      被应用的View
     * @param attrId 资源id
     * @param theme  主题
     * @return true:应用成功； false:应用失败
     */
    boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme);

}
