package vaycent.mygoal;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.alibaba.android.arouter.facade.annotation.Route;

import vaycent.RouterPath;
import vaycent.base.BaseActivity;


@Route(path = RouterPath.MY_GOAL)
public class MyGoalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mygoal_share, menu);
        menu.findItem(R.id.item_mygoal_share).setOnMenuItemClickListener(item -> {
            Log.e("111","222");
            Log.e("333","444");
            new ShareAction(MainActivity.this)
                    .withText("hello")
                    .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                    .setCallback(umShareListener)
                    .open();

            return false;
        });
        return true;
    }
}
