package vaycent.mygoal;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import vaycent.service.base.BaseActivity;


@Route(path = "/vaycent/mygoal/MyGoalActivity")
public class MyGoalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goal);
    }
}
