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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_switch).setOnClickListener(this);

        Log.e("TAG", "onCreate()");

    }

    static boolean night = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
//                getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

//                final int newNightMode = (night = !night)
//                        ? Configuration.UI_MODE_NIGHT_YES
//                        : Configuration.UI_MODE_NIGHT_NO;

                // Update the UI Mode to reflect the new night mode
//                config.uiMode = newNightMode | (config.uiMode & ~Configuration
// .UI_MODE_NIGHT_MASK);
//                getResources().updateConfiguration(config, metrics);


//                updateNightMode(night = !night);

//                UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
//                uiModeManager.setNightMode((night = !night)
//                        ? UiModeManager.MODE_NIGHT_YES : UiModeManager.MODE_NIGHT_NO);

                getDelegate().setLocalNightMode((night = !night)
                        ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

//                printConfig("TAG");
                recreate();
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
