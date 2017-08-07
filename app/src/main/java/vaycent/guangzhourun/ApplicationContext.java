package vaycent.guangzhourun;

import android.app.Application;

/**
 * Created by vaycent on 2017/8/7.
 */

public class ApplicationContext extends Application{

    private static ApplicationContext application;

    public ApplicationContext() {
        application = this;
    }

    public static ApplicationContext getApplication() {
        return application;
    }
}
