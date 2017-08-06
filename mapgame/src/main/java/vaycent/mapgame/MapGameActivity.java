package vaycent.mapgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

import vaycent.base.MapUtils;

public class MapGameActivity extends AppCompatActivity {

    private MapView mMapView;
    private MapUtils mMapUtils = new MapUtils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_game);

        initLayout();

        initMap(savedInstanceState);


    }

    private void initLayout(){

    }

    private void initMap(Bundle savedInstanceState){
        mMapView = (MapView) findViewById(R.id.activity_map_game_mv_map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap mAMap = mMapView.getMap();
        mMapUtils.setControllerShow(mAMap,false,false,false);

//        MyLocationStyle myLocationStyle  = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        mAMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
//        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }
}
