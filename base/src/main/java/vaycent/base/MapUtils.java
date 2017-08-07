package vaycent.base;


import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

public class MapUtils {

    public MapUtils(){
    }
    /* 控制地图上控件显示 */
    public void setControllerShow(AMap mAMap, boolean logo, boolean location, boolean scale, boolean compass){
        UiSettings uiSettings = mAMap.getUiSettings();
        uiSettings.setLogoBottomMargin(logo?0:-50);
        mAMap.getUiSettings().setMyLocationButtonEnabled(location);
        mAMap.getUiSettings().setZoomControlsEnabled(scale);
        mAMap.getUiSettings().setCompassEnabled(compass);

    }

    /*  */
    public void moveCameraIncludeLatlngs(AMap mAMap, LatLng start, LatLng end){
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(start)
                .include(end)
                .build();

        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
    }

    /* 增加一个Poi Marker到地图上 */
    public void addPoiMarkerToMap(Context context,AMap mAMap, LatLng pointLatlng, String title, String msg){
        //绘图部分
        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions.position(pointLatlng);
        mMarkerOptions.title(title);
        mMarkerOptions.snippet(msg);
        mMarkerOptions.draggable(false);//设置marker是否可拖动
        mMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_mapgame_pointmarker)));
        mMarkerOptions.setFlat(true);//设置贴地效果，可双指下拉地图查看效果
        Marker marker = mAMap.addMarker(mMarkerOptions);
        //动画部分
//        Animation animation = new RotateAnimation(marker.getRotateAngle(),marker.getRotateAngle()+180,0,0,0);
//        animation.setDuration(5000);
//        animation.setInterpolator(new LinearInterpolator());
//        marker.setAnimation(animation);
//        marker.startAnimation();
    }

}
