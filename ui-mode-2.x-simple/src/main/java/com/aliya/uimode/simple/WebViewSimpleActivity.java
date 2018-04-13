package com.aliya.uimode.simple;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.aliya.uimode.simple.base.BaseActivity;

/**
 * WebView相关示例
 *
 * @author a_liYa
 * @date 2018/4/13 上午9:35.
 */
public class WebViewSimpleActivity extends BaseActivity implements View.OnClickListener {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_simple);

        mWebView = (WebView) findViewById(R.id.web_view);

//        mWebView.setBackgroundColor(Color.TRANSPARENT);


        findViewById(R.id.tv_load).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mWebView.loadUrl("https://www.baidu.com/");
    }

}