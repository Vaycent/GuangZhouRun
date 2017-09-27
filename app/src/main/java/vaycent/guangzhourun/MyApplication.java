package vaycent.guangzhourun;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

import vaycent.base.BaseApplication;

import static android.app.UiModeManager.MODE_NIGHT_YES;

public class MyApplication extends BaseApplication {
    private ApplicationLike tinkerApplicationLike;

    public MyApplication() {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initTinkerPatch();

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);

    }

    private void initTinkerPatch() {
        if (BuildConfig.TINKER_ENABLE) {

            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            //开始检查是否有补丁，这里配置的是每隔访问3小时服务器是否有更新。
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
//            TinkerPatch.with().fetchPatchUpdate(true);
                    .setFetchPatchIntervalByHours(3);


            // 获取当前的补丁版本
            Log.d("Tinker", "current patch version is " + TinkerPatch.with().getPatchVersion());

            //每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
    }



}
