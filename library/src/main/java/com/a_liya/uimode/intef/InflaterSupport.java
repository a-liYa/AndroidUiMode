package com.a_liya.uimode.intef;

/**
 * 判断能否支持UiMode的属性、View的属性 - 接口
 *
 * @author a_liYa
 * @date 2017/6/26 18:16.
 */
public interface InflaterSupport {

    boolean isSupportApply(String key);

    boolean isSupportAttrId(Integer attrId);

}
