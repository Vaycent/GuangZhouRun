package vaycent.service.base;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

import vaycent.service.helper.AppBlockCanaryContext;

/**
 * Created by vaycent on 2017/8/16.
 */

public class BaseApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        initUmengAnalytics();

        initLeakCanary();

        initBlockCanary();

    }

    private void initUmengAnalytics(){
        /*
            EScenarioType. E_UM_NORMAL　　普通统计场景类型
            EScenarioType. E_UM_GAME     　　游戏场景类型
            EScenarioType. E_UM_ANALYTICS_OEM  统计盒子场景类型
            EScenarioType. E_UM_GAME_OEM      　 游戏盒子场景类型
         */
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        MobclickAgent.setDebugMode( true );
    }

    private void initLeakCanary(){
        LeakCanary.install(this);
    }

    private void initBlockCanary(){
        BlockCanary.install(this,new AppBlockCanaryContext()).start();
    }

}
