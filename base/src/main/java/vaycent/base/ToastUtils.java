package vaycent.base;

import android.content.Context;
import android.widget.Toast;


public class ToastUtils {

    private  String oldMsg;
    protected  Toast toast   = null;
    private  long oneTime=0;
    private  long twoTime=0;

    public void showToast(Context context, String s){
        if(toast==null){
            toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime>Toast.LENGTH_SHORT){
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


    public  void showToast(Context context, int resId){
        showToast(context, context.getString(resId));
    }

    public  void cancelToast(){
        if(null!=toast){
            toast.cancel();
        }
    }
}