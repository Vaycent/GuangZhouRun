package vaycent.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import vaycent.framework.helper.AppBlockCanaryContext;

import static vaycent.libs.BuildConfig.appIsRelease;



public class BaseApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        initARouter();

        UMShareAPI.get(this);

        if(appIsRelease){
            initUmengAnalytics();
        }else{
            initLeakCanary();
            initBlockCanary();
        }

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
        if (!appIsRelease) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }





}
