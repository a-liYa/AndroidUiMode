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

    protected static boolean argsValid(View v, @AttrRes int attrId, Resources.Theme theme) {
        return v != null && theme != null && UiMode.attrIdValid(attrId);
    }

}
