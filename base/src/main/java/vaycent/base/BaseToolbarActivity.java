package vaycent.base;


import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import vaycent.framework.utils.StatusbarUtils;

public class BaseToolbarActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusbarUtils.translucentStatusBar(this,true);
        }
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
