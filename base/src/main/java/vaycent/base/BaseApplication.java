package vaycent.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

import vaycent.helper.AppBlockCanaryContext;

import static vaycent.libs.BuildConfig.appIsDebug;

/**
 * Created by vaycent on 2017/8/16.
 */

public class BaseApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        initARouter();

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

    private void initARouter(){
        if (appIsDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }else{
//            ARouter.openLog();
//            ARouter.openDebug();
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }





}
