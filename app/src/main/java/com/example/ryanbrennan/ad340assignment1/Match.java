package com.example.ryanbrennan.ad340assignment1;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Match implements Parcelable{
    public String uid;
    public String name;
    public String imageUrl;
    public boolean liked;
    public String lat;
    public String longitude;

    public Match() {
    }

//    public Match(String name, boolean liked, String imageUrl) {
//        this.name = name;
//        this.liked = liked;
//        this.imageUrl = imageUrl;
//    }

//    public Match(Parcel in) {
//        name = in.readString();
//        imageUrl = in.readString();
//        liked = in.readByte() != 0;
//        uid = in.readString();
//    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match();
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("uid", uid);
//        result.put("name", name);
//        result.put("liked", liked);
//        result.put("imageUrl", imageUrl);
//
//        return result;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeByte((byte) (liked ? 1 : 0));
        dest.writeString(lat);
        dest.writeString(longitude);
    }
}
