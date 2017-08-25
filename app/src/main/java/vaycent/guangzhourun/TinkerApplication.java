package vaycent.guangzhourun;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

@DefaultLifeCycle(
        application = "vaycent.guangzhourun.MainApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL
)

public class TinkerApplication extends DefaultApplicationLike{
    public TinkerApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }//BaseApplication


    @Override
    public void onBaseContextAttached(Context context){
        super.onBaseContextAttached(context);
        TinkerInstaller.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);

    }









}
