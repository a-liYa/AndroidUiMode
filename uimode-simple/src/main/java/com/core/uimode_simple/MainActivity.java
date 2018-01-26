package com.core.uimode_simple;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;

import com.aliya.uimode.UiModeManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UiModeManager.setInflaterFactor(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_switch).setOnClickListener(this);

        Log.e("TAG", "" + R.mipmap.ic_launcher);
    }

    static boolean night = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:

                getDelegate().setLocalNightMode((night = !night)
                        ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

                break;
        }
    }

    private void printConfig(String tag) {
        Resources sRes = super.getResources();
        Configuration config = sRes.getConfiguration();
        Log.e(tag, config.hashCode() + " - " + config.uiMode);

        try {
            final ActivityInfo info = getPackageManager().getActivityInfo(
                    new ComponentName(this, getClass()), 0);
                    Log.e("TAG", "theme " + info.theme);

            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), 0);
            Log.e("TAG", "App theme " + appInfo.theme);
            Log.e("TAG", "App theme " + R.style.AppTheme);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


}
