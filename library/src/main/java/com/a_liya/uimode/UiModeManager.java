package com.a_liya.uimode;

import android.content.Context;
import android.text.TextUtils;

import com.a_liya.uimode.mode.UiView;

import java.util.HashSet;
import java.util.Set;

/**
 * UiMode管理类
 *
 * @author a_liYa
 * @date 2017/6/23 10:51.
 */
public class UiModeManager {

    private static Context sContext;

    private static Set<Integer> sSupportAttrIds;

    /**
     * 初始化： 只为持有一个ApplicationContext的引用
     *
     * @param context
     */
    public static final void init(Context context, int[] attrs) {
        sContext = context.getApplicationContext();
        addSupportAttrIds(attrs);
    }

    public static void addSupportAttrIds(int[] attrs) {
        if (attrs == null) return;
        if (sSupportAttrIds == null) {
            synchronized (UiModeManager.class) {
                if (sSupportAttrIds == null)
                    sSupportAttrIds = new HashSet<>(attrs.length);
            }
        }

        for (int attrId : attrs) {
            sSupportAttrIds.add(attrId);
        }
    }

    /**
     * 解析属性值对应真正的资源id
     *
     * @param attrVal 属性值（String）
     * @return 资源id
     */
    public static int parseAttrValue(String attrVal) {
        if (!TextUtils.isEmpty(attrVal) && attrVal.startsWith("?")) {
            String subStr = attrVal.substring(1, attrVal.length());
            try {
                Integer attrId = Integer.valueOf(subStr);

                if (isSupportAttrId(attrId)) {
                    return attrId;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return UiView.NO_ATTR_ID;
    }

    private static boolean isSupportAttrId(Integer attrId) {
        return sSupportAttrIds != null && sSupportAttrIds.contains(attrId);
    }

}
