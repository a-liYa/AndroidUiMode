package com.aliya.uimode.mode;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 支持应用ui mode的属性名称
 *
 * @author a_liYa
 * @date 2017/7/14 23:26.
 */
public final class Attr {

    public static final String NAME_THEME = "theme";
    public static final String NAME_BG = "background";
    public static final String NAME_FG = "foreground";
    public static final String NAME_ALPHA = "alpha";
    public static final String NAME_TC = "textColor";
    public static final String NAME_TCH = "textColorHint";
    public static final String NAME_DIVIDER = "divider";
    public static final String NAME_SRC = "src";
    public static final String NAME_NI = "navigationIcon";
    public static final String NAME_BUTTON = "button";
    public static final String NAME_PROGRESS_DRAWABLE = "progressDrawable";
    public static final String NAME_THUMB = "thumb";
    public static final String NAME_DRAWABLE_TOP = "drawableTop";
    public static final String NAME_DRAWABLE_BOTTOM = "drawableBottom";
    public static final String NAME_DRAWABLE_RIGHT = "drawableRight";
    public static final String NAME_DRAWABLE_LEFT = "drawableLeft";

    /**
     * @see com.aliya.uimode.R.attr#invalidate
     */
    public static final String INVALIDATE = "invalidate";
    /**
     * @see com.aliya.uimode.R.attr#invalidate
     */
    public static final String IGNORE = "uiMode_ignore";

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 帮助构建 {@link Map} mAttrIdsMap
     */
    public static final class Builder {

        private Map<String, ResourceEntry> mAttrIdsMap;

        public Builder() {
            mAttrIdsMap = new HashMap<>();
        }

        /**
         * 添加属性
         *
         * @param key    .
         * @param attrId .
         * @return builder this
         * @see #add(String, ResourceEntry)
         */
        @Deprecated
        public Builder add(String key, int attrId) {
            if (!TextUtils.isEmpty(key)) {
                mAttrIdsMap.put(key, new ResourceEntry(attrId, Type.ATTR));
            }
            return this;
        }

        public Builder add(String key, ResourceEntry entry) {
            if (!TextUtils.isEmpty(key) && entry != null) {
                mAttrIdsMap.put(key, entry);
            }
            return this;
        }

        public Map<String, ResourceEntry> build() {
            return mAttrIdsMap;
        }
    }

}
