package vaycent.guangzhourun;

import vaycent.service.base.BaseApplication;

/**
 * Created by vaycent on 2017/8/7.
 */

public class MainApplication extends BaseApplication {

    private static MainApplication application;

    public MainApplication() {
        application = this;
    }

    public static MainApplication getApplication() {
        return application;
    }
}
