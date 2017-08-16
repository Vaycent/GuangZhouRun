package vaycent.service.base;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by vaycent on 2017/8/16.
 */

public class BaseApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        initUmengAnalytics();



    }

    private void initUmengAnalytics(){
        /*
            EScenarioType. E_UM_NORMAL　　普通统计场景类型
            EScenarioType. E_UM_GAME     　　游戏场景类型
            EScenarioType. E_UM_ANALYTICS_OEM  统计盒子场景类型
            EScenarioType. E_UM_GAME_OEM      　 游戏盒子场景类型
         */
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

    }

}
