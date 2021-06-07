package com.aliya.uimode.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ConfigChangesReceiver
 *
 * @author a_liYa
 * @date 2021/4/4 10:13.
 */
public class ConfigChangesReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAG", "ConfigChanges onReceive: ");
    }
}
