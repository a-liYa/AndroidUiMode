package com.aliya.uimode.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.aliya.uimode.sample.base.BaseActivity;

/**
 * TextView 示例页
 *
 * @author a_liYa
 * @date 2018/11/12 下午3:45.
 */
public class TextViewSimpleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_simple);

        ((View)findViewById(R.id.rb_top).getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = findViewById(R.id.rb_top);
                radioButton.setChecked(!radioButton.isChecked());
            }
        });
    }
}
