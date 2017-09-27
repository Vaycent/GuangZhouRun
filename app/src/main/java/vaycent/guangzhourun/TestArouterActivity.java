package vaycent.guangzhourun;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import vaycent.base.BaseActivity;


@Route(path = "/app/TestArouterActivity")
public class TestArouterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_game);
    }
}
