package vaycent.guangzhourun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vaycent.service.router.XRouter;
import vaycent.service.router.XRules;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
    }

    private void initLayout(){
        findViewById(R.id.activity_main_btn_tomapgame).setOnClickListener(v ->{
            XRouter.getRaw(XRules.IMapGameActivity.class,this).start();});
    }
}
