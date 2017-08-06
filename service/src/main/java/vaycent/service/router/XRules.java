package vaycent.service.router;


import vaycent.service.router.annotations.ClassName;

public class XRules {

    public interface IMapGameActivity{
//        @IntentFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        IntentWrapper toNewBookingActivity(@Key("DepPort") String depPort,
//                                           @Key("ArrPort") String arrPort,
//                                           @Key("FlightDate") String flightDate,
//                                           @Key("FlightBackDate") String flightBackDate,
//                                           @Key("AdultNum") @DefaultInt(1) Integer adultNum,
//                                           @Key("ChildNum") @DefaultInt(0) Integer childNum);
        @ClassName(XConst.MAP_GAME_ACTIVITY)
        IntentWrapper toIMapGameActivity();
    }
}
