package com.grupio.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JSN on 7/7/16.
 */
public class Preferences {

    private static Preferences mPreferences;
    private Context mContext;
    private static final String SHARED_PREFRENCES = "GrupioPrefs";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    private Preferences(Context mContext) {
        this.mContext = mContext;
    }

    public static Preferences getInstances(Context mContext) {
        if (mPreferences == null) {
            mPreferences = new Preferences(mContext);
        }

        pref = mContext.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE);
        editor = pref.edit();

        return mPreferences;
    }

    public void setUnreadMessageCount(String msgCount) {
        editor.putString("UnreadMessages", msgCount).commit();
    }

    public String getUnreadMessages() {
        return pref.getString("UnreadMessages", "0");
    }

    public void setTotaldMessageCount(String msgCount) {
        editor.putString("TotalMessages", msgCount).commit();
    }

    public String getTotalMessages() {
        return pref.getString("TotalMessages", "0");
    }

    public void setIsAppVisited(boolean flag) {
        editor.putBoolean("isvisited", flag).commit();
    }

    public Boolean isAppVisited() {
//        return true;
        return pref.getBoolean("isvisited", false);
    }

    public void setEventId(String eventId){
        editor.putString("event_id", eventId).commit();
    }

    public String getEventId(){
        return "151";
//        return pref.getString("event_id", null);
    }

    public void setLoginRequired(boolean loginRequired){
        editor.putBoolean("loginRequired", loginRequired).commit();
    }

    public boolean getIsLoginRequired(){
        return pref.getBoolean("loginRequired", false);
    }

    public void setLocale(String localeStr){
        editor.putString("locale_string", localeStr).commit();
    }

    public String getLocale(){
        return pref.getString("locale_string", "");
    }
}
