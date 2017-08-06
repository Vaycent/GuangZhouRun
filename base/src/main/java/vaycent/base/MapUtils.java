package vaycent.base;


//  ViewGroup   = (ViewGroup) getChildAt(0);//地图框架
// child.getChildAt(0).setVisibility(View.VISIBLE);//地图
//child.getChildAt(2).setVisibility(View.GONE);//logo
// child.getChildAt(5).setVisibility(View.VISIBLE);//缩放按钮
// child.getChildAt(6).setVisibility(View.VISIBLE);//定位按钮
// child.getChildAt(7).setVisibility(View.VISIBLE);//指南针

import com.amap.api.maps.AMap;
import com.amap.api.maps.UiSettings;

public class MapUtils {

    public MapUtils(){
    }
    /* 控制地图上控件显示 */
    public void setControllerShow(AMap mAMap, boolean logo, boolean scale, boolean compass){
        UiSettings uiSettings = mAMap.getUiSettings();
        uiSettings.setLogoBottomMargin(logo?0:-50);
        mAMap.getUiSettings().setZoomControlsEnabled(scale);
        mAMap.getUiSettings().setCompassEnabled(compass);

    }

}
