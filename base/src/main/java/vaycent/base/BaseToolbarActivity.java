package vaycent.base;


import android.os.Build;
import android.os.Bundle;

import vaycent.framework.utils.StatusbarUtils;

public class BaseToolbarActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusbarUtils.translucentStatusBar(this,true);
        }
    }
}
