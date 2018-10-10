package com.aliya.uimode.simple;

import android.app.Application;

/**
 * Application
 *
 * @author a_liYa
 * @date 2018/1/23 10:05.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UiMode.init(App.this);

    }
}
