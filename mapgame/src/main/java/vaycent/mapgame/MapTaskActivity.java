package vaycent.mapgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MapTaskActivity extends AppCompatActivity {

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
        if(null != mapGameObj){
            ((TextView)findViewById(R.id.activity_map_task_tv_question)).setText(mapGameObj.getQuestion());
            ((TextView)findViewById(R.id.activity_map_task_tv_hint)).setText(mapGameObj.getHint());


            findViewById(R.id.activity_map_task_btn_submit).setOnClickListener(v ->{
                if(((EditText)findViewById(R.id.activity_map_task_et_answer)).getText().toString().trim().equals(mapGameObj.getAnswer())){
                    Toast.makeText(this,"恭喜你通关",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this,"答案错误",Toast.LENGTH_SHORT).show();
                }
            });


        }


    }
}
