package com.aliya.uimode;

import com.aliya.uimode.intef.InflaterSupport;
import com.aliya.uimode.intef.UiApply;

/**
 * InflaterSupportImpl
 *
 * @author a_liYa
 * @date 2020/9/27 0:00.
 */
public class InflaterSupportImpl implements InflaterSupport {

    @Override
    public boolean isSupportApply(String name) {
        return UiModeManager.sSupportApplies.containsKey(name);
    }

    @Override
    public boolean isSupportApplyType(String name, String type) {
        UiApply uiApply = UiModeManager.sSupportApplies.get(name);
        return uiApply != null && uiApply.isSupportType(type);
    }

    @Override
    public boolean isSupportAttrId(Integer attrId) { // sSupportAttrIds == null 支持所有
        return UiModeManager.sSupportAttrIds == null
                || UiModeManager.sSupportAttrIds.contains(attrId);
    }
}
