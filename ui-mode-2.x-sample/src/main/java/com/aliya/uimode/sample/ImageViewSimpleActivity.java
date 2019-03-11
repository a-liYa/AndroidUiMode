package com.aliya.uimode.sample;

import android.os.Bundle;
import android.widget.ImageView;

import com.aliya.uimode.mode.Attr;
import com.aliya.uimode.sample.base.BaseActivity;
import com.aliya.uimode.utils.UiModes;

/**
 * ImageView相关的UiMode使用示例页
 *
 * @author a_liYa
 * @date 2018/2/7 下午6:34.
 */
public class ImageViewSimpleActivity extends BaseActivity {

    ImageView ivWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_simple);

        ivWord = (ImageView) findViewById(R.id.iv_word);
        UiModes.applySave(ivWord, Attr.NAME_SRC, R.mipmap.ic_ui_mode_word);

    }

}