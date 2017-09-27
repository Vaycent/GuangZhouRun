package vaycent.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


public class ToastSharp {

    private String oldMsg;
    private Toast toast = null;
    private long oneTime=0;
    private long twoTime=0;
    private int toastDuration = Toast.LENGTH_SHORT;

    /* 改变Toast的持续时间为长显示 */
    public void setLongToastDuration(){
        toastDuration = Toast.LENGTH_LONG;
    }

    /* 改变Toast的持续时间为短显示 */
    public void setShortToastDuration(){
        toastDuration = Toast.LENGTH_SHORT;
    }

    /* 显示Toast，过滤过多toast */
    public void showToast(Context context, String s){
        if(toast==null){
            toast =Toast.makeText(context, s, toastDuration);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime>toastDuration){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime=twoTime;
    }

    /* 多态方法，可以显示resource id的文字*/
    public  void showToast(Context context, int resId){
        showToast(context, context.getString(resId));
    }

    /* 用来主动取消toast，例如在Activity的onDestroy中取消*/
    public  void cancelToast(){
        if(null!=toast){
            toast.cancel();
        }
    }

    /* 自定义Toast的显示样式 */
    public void setupMyToast(Context context, int layoutResId){
        if(null!=toast){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(layoutResId, null);

            toast.setView(v);
            toast.show();
        }

    }
}