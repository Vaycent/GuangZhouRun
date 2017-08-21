package vaycent.service.router;


import android.os.Parcelable;

import vaycent.service.router.annotations.ClassName;
import vaycent.service.router.annotations.Key;

public class XRules {


    @ClassName(XConst.MAP_GAME_ACTIVITY)
    public interface IMapGameActivity {
    }

    @ClassName(XConst.MY_GOAL_ACTIVITY)
    public interface IMyGoalActivity {
    }

    @ClassName(XConst.MAP_TASK_ACTIVITY)
    public interface IMapTaskActivity {
        IntentWrapper to(@Key("mapTaskObj") Parcelable mapTaskObj);}
}
