package com.a_liya.uimode.utils;

/**
 * 校验工具类
 *
 * @author a_liYa
 * @date 2017/6/23 11:23.
 */
public class CheckUtils {

    /**
     * 校验 resid 是否有效
     *
     * @param resid 资源id
     * @return true 有效资源, false 无效资源
     */
    public static boolean residValid(int resid) {
        return resid > 0;
    }

}
