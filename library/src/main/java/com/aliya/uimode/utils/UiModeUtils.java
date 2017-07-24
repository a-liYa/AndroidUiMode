package com.aliya.uimode.utils;

import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.widget.ImageView;

import com.aliya.uimode.UiModeManager;
import com.aliya.uimode.intef.UiApply;
import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.mode.UiMode;

/**
 * 帮助解析资源保存资源 - 工具类
 *
 * @author a_liYa
 * @date 2017/7/24 10:05.
 */
public class UiModeUtils {

    public static void applyImageSrc(ImageView v, @AttrRes int attrId, Resources.Theme theme) {
        if (v == null || theme == null) return;

        UiApply uiApply = UiModeManager.get().obtainApplyPolicy(Attr.NAME_SRC);
        if (uiApply != null) {
            if (uiApply.onApply(v, attrId, theme)) {
                UiMode.putUiModeView(v, Attr.builder().add(Attr.NAME_SRC, attrId).build());
            }
        }

    }

    public static void applyImageSrc(ImageView v, @AttrRes int attrId) {
        if (v == null || v.getContext() == null) return;

        applyImageSrc(v, attrId, v.getContext().getTheme());
    }

}
