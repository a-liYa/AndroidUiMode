package com.aliya.uimode.intef;

import android.util.TypedValue;
import android.view.View;

import com.aliya.uimode.mode.ResourceEntry;

/**
 * 适配UiMode的对应属性执行的方法 - 接口
 * <p>
 * 具体属性一一对应{@link UiApply}具体实现类
 * </p>
 * <p>
 * 为了简便开发可继承 {@link com.aliya.uimode.apply.AbsApply}
 * </p>
 *
 * @author a_liYa
 * @date 2017/6/26 12:33.
 */
public interface UiApply {

    TypedValue sOutValue = new TypedValue();

    /**
     * apply对应主题的资源
     *
     * @param v     被应用的View
     * @param entry 资源实体类
     * @return true:应用成功； false:应用失败
     */
    boolean onApply(View v, ResourceEntry entry);

    /**
     * 是否为当前属性支持的类型 {@link android.content.res.Resources#getResourceTypeName(int)}
     *
     * @param type A string holding the type name of the resource
     * @return true:支持
     */
    boolean isSupportType(String type);

}
