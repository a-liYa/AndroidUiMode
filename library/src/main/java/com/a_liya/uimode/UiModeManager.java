package com.a_liya.uimode;

import android.content.Context;
import android.text.TextUtils;

import com.a_liya.uimode.intef.ApplyPolicy;
import com.a_liya.uimode.apply.ApplyAlpha;
import com.a_liya.uimode.apply.ApplyBackground;
import com.a_liya.uimode.apply.ApplyDivider;
import com.a_liya.uimode.apply.ApplyForeground;
import com.a_liya.uimode.apply.ApplySrc;
import com.a_liya.uimode.apply.ApplyTextColor;
import com.a_liya.uimode.intef.UiApply;
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
public class UiModeManager implements ApplyPolicy {

    private static Context sContext;

    private static Set<Integer> sSupportAttrIds;
    private static Map<String, UiApply> sUiApplyMap = new HashMap<>();

    static {
        sUiApplyMap.put("background", new ApplyBackground());
        sUiApplyMap.put("foreground", new ApplyForeground());
        sUiApplyMap.put("alpha", new ApplyAlpha());
        sUiApplyMap.put("textColor", new ApplyTextColor());
        sUiApplyMap.put("divider", new ApplyDivider());
        sUiApplyMap.put("src", new ApplySrc());
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
        // 不存在，创建
        if (sSupportAttrIds == null) {
            synchronized (UiModeManager.class) {
                if (sSupportAttrIds == null)
                    sSupportAttrIds = new HashSet<>(attrs.length);
            }
        }
        // 添加全部
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

    @Override
    public UiApply obtainApplyPolicy(String key) {
        if (sUiApplyMap != null) {
            return sUiApplyMap.get(key);
        }
        return null;
    }
}
