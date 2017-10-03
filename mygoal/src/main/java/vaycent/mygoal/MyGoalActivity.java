package vaycent.mygoal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import vaycent.RouterPath;
import vaycent.base.BaseActivity;


@Route(path = RouterPath.MY_GOAL)
public class MyGoalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goal);

        initToolBar();
    }

    private void initToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_activity_my_goal_toolbar);
////        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(v -> {
//            onBackPressed();
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mygoal_share, menu);
        menu.findItem(R.id.item_mygoal_share).setOnMenuItemClickListener(item -> {
            Log.e("111","222");
            Log.e("333","444");
//            new ShareAction(MyGoalActivity.this)
//                    .withText("hello")
//                    .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
//                    .setCallback(umShareListener)
//                    .withText("Test my share")
//                    .open();
//            new ShareAction(ShareActivity.this).withText("hello").withMedia(image).share();

            new ShareAction(MyGoalActivity.this)
                    .withText("Test my share api")
                    .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                    .setCallback(umShareListener)
                    .open();

            return false;
        });
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }





    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Toast.makeText(MyGoalActivity.this,platform.toString(),Toast.LENGTH_LONG).show();

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MyGoalActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MyGoalActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MyGoalActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };
}
