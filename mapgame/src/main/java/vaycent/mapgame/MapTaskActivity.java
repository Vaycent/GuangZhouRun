package vaycent.mapgame;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import vaycent.RouterPath;
import vaycent.base.BaseActivity;

@Route(path = RouterPath.MAP_TASK)
public class MapTaskActivity extends BaseActivity {

    private MapGameObj mapGameObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_task);

        initData();

        initLayout();
    }

    private void initData(){
        mapGameObj = getIntent().getParcelableExtra("mapTaskObj");
    }

    private void initLayout(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            StatusbarUtils.translucentStatusBar(this,true);
//        }
//        getSupportActionBar().hide();

        if(null != mapGameObj){
            ((TextView)findViewById(R.id.activity_map_task_tv_question)).setText(mapGameObj.getQuestion());
            ((TextView)findViewById(R.id.activity_map_task_tv_hint)).setText(mapGameObj.getHint());


            findViewById(R.id.activity_map_task_btn_submit).setOnClickListener(v ->{
                ARouter.getInstance().build(RouterPath.MY_GOAL).navigation();

//                MobclickAgent.onEvent(this, "000003");
//                if(((EditText)findViewById(R.id.activity_map_task_et_answer)).getText().toString().trim().equals(mapGameObj.getAnswer())){
//                    Toast.makeText(this,"恭喜你通关",Toast.LENGTH_SHORT).show();
//                    finish();
//                }else{
//                    Toast.makeText(this,"答案错误",Toast.LENGTH_SHORT).show();
//                }
            });

            Glide.with(this).load(R.drawable.ic_game_bg).thumbnail(0.001f).into((ImageView)findViewById(R.id.activity_map_task_iv_taskpic));

        }
    }




}
