package com.aliya.uimode.intef;

/**
 * UiMode切换回调 - 接口，自定义控件可实现此接口及时刷新界面，Activity可实现此接口监听UiMode切换
 *
 * @author a_liYa
 * @date 16/11/9 15:40.
 */
public interface UiModeChangeListener {

    void onUiModeChange();

}
