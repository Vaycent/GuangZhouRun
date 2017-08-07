package vaycent.mapgame;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.maps.model.LatLng;

/**
 * Created by vaycent on 2017/8/7.
 */

public class MapGameObj implements Parcelable {

    private int id;
    private LatLng pointLatLng;
    private String question;
    private String hint;
    private String answer;
    private String picStr;


    public MapGameObj(int id, LatLng pointLatLng, String question, String hint, String answer, String picStr) {
        this.id = id;
        this.pointLatLng = pointLatLng;
        this.question = question;
        this.hint = hint;
        this.answer = answer;
        this.picStr = picStr;

    }

    public MapGameObj(Parcel in) {
        id = in.readInt();
        pointLatLng = in.readParcelable(LatLng.class.getClassLoader());
        question = in.readString();
        hint = in.readString();
        answer = in.readString();
        picStr = in.readString();
    }

    public int getId(){return id;}
    public LatLng getLatLng(){return pointLatLng;}
    public String getQuestion(){return question;}
    public String getHint(){return hint;}
    public String getAnswer(){return answer;}
    public String getPicStr(){return picStr;}






    public static final Creator<MapGameObj> CREATOR = new Creator<MapGameObj>() {
        public MapGameObj createFromParcel(Parcel in) {
            return new MapGameObj(in);
        }
        public MapGameObj[] newArray(int size) {
            return new MapGameObj[size];
        }
    };



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(pointLatLng,flags);
        dest.writeString(question);
        dest.writeString(hint);
        dest.writeString(answer);
        dest.writeString(picStr);
    }

    public int describeContents() {
        return 0;
    }
}
