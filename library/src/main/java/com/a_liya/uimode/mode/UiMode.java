package com.a_liya.uimode.mode;

import android.content.res.Resources.Theme;
import android.view.View;

import com.a_liya.uimode.R;
import com.a_liya.uimode.intef.ApplyPolicy;
import com.a_liya.uimode.intef.UiApply;

import java.util.HashMap;
import java.util.Map;

/**
 * UiMode相关操作
 *
 * @author a_liYa
 * @date 2017/6/23 11:07.
 */
public class UiMode {

    public static final int NO_ATTR_ID = -1;

    public static void saveAttrIds(View v, Map<String, Integer> attrs) {
        if (v != null && attrs != null) {
            Map<String, Integer> attrIds = new HashMap<>(attrs.size());
            attrIds.putAll(attrs);
            v.setTag(R.id.tag_ui_mode, attrIds);
        }
    }

    public static void apply(View v, Theme theme, ApplyPolicy policy) {
        if (v == null) return;

        Map<String, Integer> attrIds = null;
        Object vTag = v.getTag(R.id.tag_ui_mode);
        if (vTag instanceof Map) {
            attrIds = (Map<String, Integer>) vTag;
        }

        if (attrIds != null && policy != null) {
            for (Map.Entry<String, Integer> entry : attrIds.entrySet()) {
                String key = entry.getKey();
                UiApply uiApply = policy.obtainApplyPolicy(key);
                if (uiApply != null) {
                    uiApply.onApply(v, entry.getValue(), theme);
                }
            }
        }
    }

    /**
     * 校验 attrId 是否有效
     *
     * @param attrId 资源id
     * @return true 有效资源, false 无效资源
     */
    public static boolean attrIdValid(int attrId) {
        return attrId != NO_ATTR_ID;
    }
}
