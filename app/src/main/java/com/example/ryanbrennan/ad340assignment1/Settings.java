package com.example.ryanbrennan.ad340assignment1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Settings {

    @PrimaryKey
    @NonNull
    private String email = "";

    @ColumnInfo(name = "reminder_time")
    private String reminderTime;

    @ColumnInfo(name = "match_distance_search")
    private int matchDistanceSearch;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "privacy")
    private boolean privacy;

    @ColumnInfo(name = "lower_age_range")
    private int lowerAgeRange;

    @ColumnInfo(name = "upper_age_range")
    private int upperAgeRange;

    public Settings() {}

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public int getMatchDistanceSearch() {
        return matchDistanceSearch;
    }

    public void setMatchDistanceSearch(int matchDistanceSearch) {
        this.matchDistanceSearch = matchDistanceSearch;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean getPrivacy(){
        return privacy;
    }

    public void setPrivacy(boolean privacy){
        this.privacy = privacy;
    }

    public int getLowerAgeRange(){
        return lowerAgeRange;
    }

    public void setLowerAgeRange(int lowerAgeRange){
        this.lowerAgeRange = lowerAgeRange;
    }

    public int getUpperAgeRange(){
        return upperAgeRange;
    }

    public void setUpperAgeRange(int upperAgeRange){
        this.upperAgeRange = upperAgeRange;
    }
}
