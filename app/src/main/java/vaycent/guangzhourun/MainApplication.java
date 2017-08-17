package vaycent.guangzhourun;

import android.support.v7.app.AppCompatDelegate;

import vaycent.service.base.BaseApplication;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

/**
 * Created by vaycent on 2017/8/7.
 */

public class MainApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        /* 使用暗色系的主题 */
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
    }







}
