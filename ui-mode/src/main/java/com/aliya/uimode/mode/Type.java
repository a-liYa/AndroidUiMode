package com.aliya.uimode.mode;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * type names for a given resource identifier
 *
 * @author a_liYa
 * @date 2018/1/24 16:12.
 */
public final class Type {

    public static final String COLOR = "color";

    public static final String DRAWABLE = "drawable";

    public static final String ATTR = "attr";

    public static final String MIPMAP = "mipmap";

    public static final String STRING = "string";

    public static final String STYLE = "style";

    public static final String DIMEN = "dimen";

    @StringDef(value = {
            COLOR,
            DRAWABLE,
            ATTR,
            MIPMAP,
            STRING,
            STYLE,
            DIMEN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResourceType {
    }

}
