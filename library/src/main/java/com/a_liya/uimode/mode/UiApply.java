package com.a_liya.uimode.mode;

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

    boolean onApply(View v, @AttrRes int attrId, Resources.Theme theme);

}
