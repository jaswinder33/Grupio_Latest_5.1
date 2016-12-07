package com.grupio.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JSN on 7/7/16.
 */
public class Preferences {

    private static final String SHARED_PREFRENCES = "GrupioPrefs";
    private static Preferences mPreferences;
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private Context mContext;

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
        return pref.getBoolean("isvisited", false);
    }

    public String getEventId() {
//        return "151";
        return pref.getString("event_id", "");
    }

    public void setEventId(String eventId) {
        editor.putString("event_id", eventId).commit();
    }

    public void setLoginRequired(boolean loginRequired) {
        editor.putBoolean("loginRequired", loginRequired).commit();
    }

    public boolean getIsLoginRequired() {
        return pref.getBoolean("loginRequired", false);
    }

    public String getLocale() {
        return pref.getString("locale_string", "");
    }

    public void setLocale(String localeStr) {
        editor.putString("locale_string", localeStr).commit();
    }

    public String getAlertCount() {
        return pref.getString("alert_count", "");
    }

    public void setAlertCount(String count) {
        editor.putString("alert_count", count).commit();
    }

    public void setCalenderCount(String count) {
        editor.putString("calendar_count", count).commit();
    }

    public String getCalendarCount() {
        return pref.getString("calendar_count", "");
    }

    public String getChatCount() {
        return pref.getString("chat_count", "");
    }

    public void setChatCount(String count) {
        editor.putString("chat_count", count).commit();
    }

    public String getAttendeeId() {
        return pref.getString("attendee_id", null);
    }

    public void setAttendeeId(String attendeeId) {
        editor.putString("attendee_id", attendeeId).commit();
    }

    public void saveUserInfo(String userInfo) {
        editor.putString("user_info", userInfo).commit();
    }

    public String getUserInfo() {
        return pref.getString("user_info", null);
    }

    public String getDeviceID() {
        return pref.getString("deviceID", "");
    }

    public void setDeviceID(String token) {
        editor.putString("deviceID", token).commit();
    }

    public void saveDeviceToken(String token) {
        editor.putString("deviceToken", token).commit();
    }

    public String getDeviceToken() {
        return pref.getString("deviceToken", "");
    }

    public String getUnreadAlertCount() {
        return pref.getString("unreadAlertCount", "0");
    }

    public void setUnreadAlertCount(String count) {
        editor.putString("unreadAlertCount", count).commit();
    }

    public String getExhibitorCategory() {
        return pref.getString("exhibitor_category", "");
    }

    public void setExhibitorCategory(String response) {
        editor.putString("exhibitor_category", response).commit();
    }

    public void setLinkedinToken(String token) {
        editor.putString("linkedin", token).commit();
    }

    public String getLinedinToken() {
        return pref.getString("linkedin", null);
    }

    public void saveBestMatch(String response) {
        editor.putString("best_match", response).commit();
    }

    public String getBestMatch() {
        return pref.getString("best_match", "");
    }

    public void setInterest(String interest) {
        editor.putString("interest", interest).commit();
    }

    public String getInterests() {
        return pref.getString("interest", null);
    }

    public void savePhotogalleryData(String response) {
        editor.putString("photo_gallery", response).commit();
    }

    public String getPhotoGalleryData() {
        return pref.getString("photo_gallery", "");
    }
}
