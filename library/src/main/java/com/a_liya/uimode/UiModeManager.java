package com.a_liya.uimode;

import android.content.Context;
import android.text.TextUtils;

import com.a_liya.uimode.mode.ApplyAlpha;
import com.a_liya.uimode.mode.ApplyBackground;
import com.a_liya.uimode.mode.ApplyDivider;
import com.a_liya.uimode.mode.ApplyForeground;
import com.a_liya.uimode.mode.ApplySrc;
import com.a_liya.uimode.mode.ApplyTextColor;
import com.a_liya.uimode.mode.UiApply;
import com.a_liya.uimode.mode.UiView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    private static Map<String, UiApply> sUiApplys = new HashMap<>();

    static {
        sUiApplys.put("background", new ApplyBackground());
        sUiApplys.put("foreground", new ApplyForeground());
        sUiApplys.put("alpha", new ApplyAlpha());
        sUiApplys.put("textColor", new ApplyTextColor());
        sUiApplys.put("divider", new ApplyDivider());
        sUiApplys.put("src", new ApplySrc());
    }

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
