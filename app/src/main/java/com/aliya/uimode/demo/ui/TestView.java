package com.aliya.uimode.demo.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义测试View
 *
 * @author a_liYa
 * @date 2017/6/23 13:33.
 */
public class TestView extends View {

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        Log.e("TAG", "defStyle " + defStyleAttr);
//        if (attrs == null) return;
//        for (int i = 0; i < attrs.getAttributeCount(); i++) {
//            String attrName = attrs.getAttributeName(i);
//            String attrValue = attrs.getAttributeValue(i);
//            if ("style".equals(attrName)) {
//
//            }
//
//            Log.e("TAG", "name " + attrName + "; value " + attrValue + "; style " + attrs.getStyleAttribute());
//
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
