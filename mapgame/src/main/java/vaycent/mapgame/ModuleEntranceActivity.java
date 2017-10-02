package vaycent.mapgame;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import vaycent.RouterPath;
import vaycent.framework.utils.StatusbarUtils;


public class ModuleEntranceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusbarUtils.translucentStatusBar(this,true);
        }

        Toast.makeText(this,RouterPath.MAP_GAME,Toast.LENGTH_SHORT).show();

        ARouter.getInstance().build(RouterPath.MAP_GAME).navigation();

        finish();
    }

}
