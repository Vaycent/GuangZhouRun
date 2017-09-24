package vaycent.mapgame;

import android.Manifest;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import vaycent.RouterPath;
import vaycent.base.AMapMapUtils;
import vaycent.base.BaseActivity;

@Route(path = RouterPath.MAP_GAME)
public class MapGameActivity extends BaseActivity {

    private MapView mMapView;
    private AMap mAMap;
    private AMapMapUtils mAMapMapUtils = new AMapMapUtils();
    private ArrayList<MapGameObj> mMapGameObjList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_game);
        requestPermission();

        initData();

        initLayout();

        initMap(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    private void initData(){
        MapGameObj mMapGameObj1 = new MapGameObj(1,new LatLng(23.126733,113.244757),"问题：1+2+实地提示=?"
                ,"提示：实地提示为3","6","");
        MapGameObj mMapGameObj2 = new MapGameObj(1,new LatLng(23.125776,113.244838),"问题：2+2+实地提示=?"
                ,"提示：实地提示为3","7","");
        MapGameObj mMapGameObj3 = new MapGameObj(1,new LatLng(23.126289,113.246114),"问题：3+2+实地提示=?"
                ,"提示：实地提示为3","8","");
        MapGameObj mMapGameObj4 = new MapGameObj(1,new LatLng(23.127429,113.246619),"问题：4+2+实地提示=?"
                ,"提示：实地提示为3","9","");
        MapGameObj mMapGameObj5 = new MapGameObj(1,new LatLng(23.125885,113.246822),"问题：5+2+实地提示=?"
                ,"提示：实地提示为3","10","");
        mMapGameObjList.add(mMapGameObj1);
        mMapGameObjList.add(mMapGameObj2);
        mMapGameObjList.add(mMapGameObj3);
        mMapGameObjList.add(mMapGameObj4);
        mMapGameObjList.add(mMapGameObj5);

    }

    private void initLayout(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                StatusbarUtils.translucentStatusBar(this,true);
//        }
//
//
//        getSupportActionBar().hide();




    }

    private void initMap(Bundle savedInstanceState){
        mMapView = (MapView) findViewById(R.id.activity_map_game_mv_map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        mAMap = mMapView.getMap();
        mAMap.setMapType(AMap.MAP_TYPE_NIGHT);
        mAMapMapUtils.setControllerShow(mAMap,false,false,false,false);

//        MyLocationStyle myLocationStyle  = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
//        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。



        for(int i=0;i<mMapGameObjList.size();i++){
            MapGameObj mMapGameObj = mMapGameObjList.get(i);
            mAMapMapUtils.addPoiMarkerToMap(this,mAMap,mMapGameObj.getLatLng(),mMapGameObj.getQuestion(),mMapGameObj.getHint());
        }


        mAMap.setOnMarkerClickListener(marker -> {
            if(null!=mAMap&&null!=marker){
                jumpPoint(marker);
                return true;
            }
            return false;
        });


        mAMap.setOnMapLoadedListener(() -> {
//            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mMapGameObjList.get(0).getLatLng()));
//            mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));

            mAMapMapUtils.moveCameraIncludeLatlngs(mAMap,mMapGameObjList.get(0).getLatLng(),mMapGameObjList.get(mMapGameObjList.size()-1).getLatLng());
        });


    }


    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mAMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }else{
                    Log.e("Vaycent","marker.getId():"+marker.getId());
                    int checkId = Integer.parseInt(marker.getId().replaceAll("Marker",""))-1;
                    checkId = checkId%mMapGameObjList.size();
                    Log.e("Vaycent","checkId:"+checkId);
                    if(checkId>=0){
                        ARouter.getInstance().build(RouterPath.MAP_TASK)
                                .withParcelable("mapTaskObj", mMapGameObjList.get(checkId))
                                .navigation();
                    }
                }
            }
        });

    }


    private void requestPermission() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission_group.LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                        Log.e("Vaycent","granted");
                    } else {
                        // At least one permission is denied
                        Log.e("Vaycent","!granted");

                    }
                });
    }

}
