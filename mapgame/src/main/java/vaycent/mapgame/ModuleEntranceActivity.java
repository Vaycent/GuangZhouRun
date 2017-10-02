package vaycent.mapgame;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vaycent.framework.utils.StatusbarUtils;

/**
 * Created by vaycent on 2017/9/30.
 */

public class ModuleEntranceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusbarUtils.translucentStatusBar(this,true);
        }
    }

}
