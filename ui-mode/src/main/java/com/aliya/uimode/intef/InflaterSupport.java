package com.aliya.uimode.intef;

/**
 * 判断能否支持UiMode的属性、View的属性 - 接口
 *
 * @author a_liYa
 * @date 2017/6/26 18:16.
 */
public interface InflaterSupport {

    /**
     * 判断是否为支持的类型 {@link UiApply}
     *
     * @param key 属性名称 {@link com.aliya.uimode.mode.Attr}
     * @return true : 支持
     */
    boolean isSupportApply(String key);

    /**
     * 判断是否为支持的属性
     *
     * @param attrId a attr id
     * @return true : 支持
     */
    boolean isSupportAttrId(Integer attrId);

}
