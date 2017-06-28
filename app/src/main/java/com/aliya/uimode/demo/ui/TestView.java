package com.aliya.uimode.demo.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.aliya.uimode.demo.R;

/**
 * 自定义测试View
 *
 * @author a_liYa
 * @date 2017/6/23 13:33.
 */
public class TestView extends View {

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parseAttr(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttr(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        parseAttr(context, attrs);

    }

    private void parseAttr(Context context, @Nullable AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SupportUiMode);

//        final int N = a.getIndexCount();
//        for (int i = 0; i < 0; i++) {
//            int attr = a.getIndex(i);
//            switch (attr) {
//                case R.styleable.SupportUiMode_android_background:
//                    int resourceId = a.getResourceId(attr, -2);
//                    Log.e("TAG", "resourceId --- " + resourceId);
//                    TypedValue value = a.peekValue(attr);
//                    if (value != null) {
//                        Log.e("TAG", "type " + value.type);
//                        Log.e("TAG", "data " + value.data);
//                        Log.e("TAG", "assetCookie " + value.assetCookie);
//                        Log.e("TAG", "changingConfigurations " + value.changingConfigurations);
//                        Log.e("TAG", "density " + value.density);
//                        Log.e("TAG", "string " + value.string);
//                        Log.e("TAG", "resourceId " + value.resourceId);
//                    }
//
//                    String resString = a.getNonResourceString(attr);
//
//                    Log.e("TAG", "resString " + resString);
//
//                    Log.e("TAG", "getInteger" + a.getInteger(attr, -2));
//                    Log.e("TAG", "getInt" + a.getInt(attr, -3));
//                    Log.e("TAG", "getString" + a.getString(attr));
//
//                    break;
//            }
//        }

        if (attrs == null) return;

        int count = attrs.getAttributeCount();

        for (int i = 0; i < count; i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

//            attrs.getAttributeNameResource(i)

            if ("alpha".equals(attrName) || "background".equals(attrName)) {
                Log.e("TAG", "name " + attrName + "; value " + attrValue);
                Log.e("TAG", "getAttributeNameResource " + attrs.getAttributeNameResource(i));
                Log.e("TAG", "getAttributeResourceValue " + attrs.getAttributeResourceValue(i, -2));
                Log.e("TAG", "getAttributeUnsignedIntValue " + attrs.getAttributeUnsignedIntValue(i,
                        -3));
                Log.e("TAG", "getAttributeIntValue " + attrs.getAttributeIntValue(i, -4));
                Log.e("TAG", "getStyleAttribute " + attrs.getStyleAttribute());
                Log.e("TAG", "getIdAttribute " + attrs.getIdAttribute());

                Log.e("TAG", "\n-----------------\n");

                Log.e("TAG", "background " + R.styleable.SupportUiMode[R.styleable.SupportUiMode_android_background]);
            }
        }

    }


}
