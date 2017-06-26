package com.a_liya.uimode.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 测试兼容性 页面
 *
 * @author a_liYa
 * @date 2017/6/23 下午12:50.
 */
public class TestCompatActivity extends Activity {

    @BindView(R.id.view)
    View mView;
    @BindView(R.id.et_num)
    EditText mEtNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_compat);
        ButterKnife.bind(this);

//        Log.e("TAG", "bg_view " + R.color.colorAccent);
//        Log.e("TAG", "ui_view " + Color.parseColor("#000000"));
//        Log.e("TAG", "ui_alpha " + R.drawable.sl_color);
//        Log.e("TAG", "\n ---- \n");
        TypedValue outValue = new TypedValue();

        boolean b = getTheme().resolveAttribute(R.attr.bg_view, outValue, true);

//        Log.e("TAG", "resolve " + b);
//        Log.e("TAG", "resourceId " + outValue.resourceId);
//        Log.e("TAG", "string " + outValue.string);
//        Log.e("TAG", "data " + outValue.data);
//        Log.e("TAG", "getFloat " + outValue.getFloat());
//        Log.e("TAG", "type " + outValue.type);
        Log.e("TAG", "\n ---- \n");

        b = getTheme().resolveAttribute(R.attr.ui_view, outValue, true);

//        Log.e("TAG", "resolve " + b);
        Log.e("TAG", "resourceId " + outValue.resourceId);
        Log.e("TAG", "string " + outValue.string);
        Log.e("TAG", "data " + outValue.data);
        Log.e("TAG", "getFloat " + big(outValue.getFloat()));
        Log.e("TAG", "type " + outValue.type);
        Log.e("TAG", "\n ---- \n");

        b = getTheme().resolveAttribute(R.attr.ui_alpha, outValue, true);

//        Log.e("TAG", "resolve " + b);
        Log.e("TAG", "resourceId " + outValue.resourceId);
        Log.e("TAG", "string " + outValue.string);
        Log.e("TAG", "data " + outValue.data);
        Log.e("TAG", "getFloat " + big(outValue.getFloat()));
        Log.e("TAG", "type " + outValue.type);
//        Log.e("TAG", "value " + getResources().getFraction());
        Log.e("TAG", "\n ---- \n");

//        Log.e("TAG", "#000" + Color.parseColor("#000"));

//        int identifier = getResources().getIdentifier("UiMode1", "styleable", getPackageName());

    }

    private static String big(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        // 是否以逗号隔开, 默认true以逗号隔开,如[123,456,789.128]
        nf.setGroupingUsed(false);
        // 结果未做任何处理
        return nf.format(d);
    }

    //        String entryName = getResources().getResourceEntryName(R.attr.bg_view);
//        String packageName = getResources().getResourcePackageName(R.attr.bg_view);
//        String typeName = getResources().getResourceTypeName(R.attr.bg_view);
//        String name = getResources().getResourceName(R.attr.bg_view);

//        Log.e("TAG","entryName " + entryName);
//        Log.e("TAG","packageName " + packageName);
//        Log.e("TAG","typeName " + typeName);
//        Log.e("TAG","name " + name);

//        mView.setBackgroundColor(ContextCompat.getColor(this, R.attr.bg_view));


    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        String s = mEtNum.getText().toString();
        long num = Long.parseLong(s);
        long startMs;
        switch (view.getId()) {
            case R.id.btn_left:
//                setTheme(R.style.AppTheme);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                    mView.setBackground(ContextCompat.getDrawable(this, R.attr.bg_view));
//                } else {
////                    mView.setBackgroundDrawable(Utils.getDrawable(this, R.attr.bg_view));
//                }

                startMs = SystemClock.uptimeMillis();
                SparseArray<Integer> sAttrArrays = new SparseArray<>();

                for (int i = 0; i < num; i++) {
                    sAttrArrays.put(i, new Integer(i));
                    sAttrArrays.put(i + 1, new Integer(i));
                    sAttrArrays.put(i + 2, new Integer(i));
                    sAttrArrays.clear();
                }
                Log.e("TAG", "复用 次数: " + num + "; 时长：" + (SystemClock.uptimeMillis() - startMs));

                break;
            case R.id.btn_right:

                startMs = SystemClock.uptimeMillis();

                for (int i = 0; i < num; i++) {
                    SparseArray<Integer> attrArrays = new SparseArray<>();
                    attrArrays.put(i, new Integer(i));
                    attrArrays.put(i + 1, new Integer(i));
                    attrArrays.put(i + 2, new Integer(i));
//                    sAttrArrays.clear();
                }
                Log.e("TAG", "创建 次数: " + num + "; 时长：" + (SystemClock.uptimeMillis() - startMs));
//                setTheme(R.style.NightAppTheme);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                    mView.setBackground(Utils.getDrawable(this, R.attr.bg_view));
//                } else {
////                    mView.setBackgroundDrawable(Utils.getDrawable(this, R.attr.bg_view));
//                }
                break;
        }
    }
}
