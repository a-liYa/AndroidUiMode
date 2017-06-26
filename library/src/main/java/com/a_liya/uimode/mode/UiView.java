package com.a_liya.uimode.mode;

import android.content.res.Resources.Theme;
import android.view.View;

import com.a_liya.uimode.intef.ApplyPolicy;
import com.a_liya.uimode.intef.UiApply;
import com.a_liya.uimode.intef.UiMode;

import java.util.HashMap;
import java.util.Map;

/**
 * View{@link android.view.View} 的 UiMode {@link UiMode}实现类
 *
 * @author a_liYa
 * @date 2017/6/23 11:07.
 */
public class UiView implements UiMode {

    private Map<String, Integer> mAttrIdMap = null;

    public static final int NO_ATTR_ID = -1;


//    /**
//     * 获取具体的ResourceId
//     *
//     * @param attrId attrId
//     * @param theme  当前Activity theme
//     * @return 返回具体ResourceId
//     */
//    public static int fetchResId(@AttrRes int attrId, Theme theme) {
//        if (theme == null) {
//            return NO_ATTR_ID;
//        }
//        theme.resolveAttribute(attrId, sOutValue, true);
//        return sOutValue.resourceId;
//    }

//    public static float fetchFloat(@AttrRes int attrId, Theme theme) {
//        if (theme == null) {
//            return 1.0f;
//        }
//        theme.resolveAttribute(attrId, sOutValue, true);
//        if (sOutValue.type == TypedValue.TYPE_FLOAT) {
//            return sOutValue.getFloat();
//        }
//        return 1.0f;
//    }

    @Override
    public void saveAttrs(Map<String, Integer> attrs) {
        if (mAttrIdMap == null) {
            mAttrIdMap = new HashMap<>(attrs.size());
        }
        mAttrIdMap.putAll(attrs);
    }

    @Override
    public void apply(View v, Theme theme, ApplyPolicy policy) {
        if (mAttrIdMap != null && policy != null) {
            for (Map.Entry<String, Integer> entry : mAttrIdMap.entrySet()) {
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
