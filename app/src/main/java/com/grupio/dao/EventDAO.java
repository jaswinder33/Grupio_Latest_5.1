package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.grupio.helper.EventDataProcessor;
import com.grupio.data.EventData;
import com.grupio.db.MyDbHandler;


/**
 * Created by JSN on 22/8/16.
 */
public class EventDAO extends BaseDAO{

    private EventDAO(Context mContext) {
        super(mContext);
    }

    public static EventDAO getInstance(Context mContext) {
        return new EventDAO(mContext);
    }


    /**
     * insert event data directly from API in db
     */
    public void insert(String responseFromAPI) {

        deleteEventData();

        EventDataProcessor edp = new EventDataProcessor();
        EventData eData = edp.parseResult(responseFromAPI, mContext);

        openDB(1);

        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + MyDbHandler.EVENT_TABLE
                            + " ( "
                            + MyDbHandler.EVENT_ID + ","
                            + MyDbHandler.EVENT_NAME + ","
                            + MyDbHandler.START_DATE + ","
                            + MyDbHandler.END_DATE + ","

                            + MyDbHandler.DESCRIPTION + ","
                            + MyDbHandler.IMAGEURL + ","
                            + MyDbHandler.LARGE_IMAGE + ","

                            + MyDbHandler.WEB_LOGO_IMAGE + ","
                            + MyDbHandler.STATIC_MAP_URL + ","

                            + MyDbHandler.ADDRESS1 + ","
                            + MyDbHandler.ADDRESS2 + ","

                            + MyDbHandler.VENUE + ","
                            + MyDbHandler.CITY + ","
                            + MyDbHandler.STATE + ","
                            + MyDbHandler.COUNTRY + ","
                            + MyDbHandler.ZIPCODE + ","

                            + MyDbHandler.TIMEZONE + ","
                            + MyDbHandler.REGISTER_URL + ","

                            + MyDbHandler.HASHTAG + ","
                            + MyDbHandler.LOGINREQUIRED + ","

                            + MyDbHandler.REGISTRATION_REQUIRED + ","
                            + MyDbHandler.ABOUT_TEXT + ","
                            + MyDbHandler.LATTITUDE + ","
                            + MyDbHandler.LONGITUDE + ","

                            + MyDbHandler.ABOUT_SECTION_TEXT + ","
                            + MyDbHandler.SOCIAL_SECTION_TEXT + ","
                            + MyDbHandler.SURVEY_LOGIN_REQ + ","
                            + MyDbHandler.MYCALENDAR_LOGIN_REQUIRED + ","
                            + MyDbHandler.MYSCHEDULE_LOGIN_ENABLED + ","

                            + MyDbHandler.HIDE_ATTENDEE_INFO + ","
                            + MyDbHandler.HIDE_SPEAKER_INFO + ","
                            + MyDbHandler.HIDE_EXHIBITOR_IMAGES + ","
                            + MyDbHandler.HIDE_SPONSOR_IMAGES + ","
                            + MyDbHandler.HIDE_SPEAKER_IMAGES + ","
                            + MyDbHandler.HIDE_ATTENDEE_IMAGES + ","

                            + MyDbHandler.COLOR_THEME + ","
                            + MyDbHandler.NOTES_LOGIN_REQUIRED + ","
                            + MyDbHandler.ATTENDEE_LOGIN_REQUIRED + ","

                            + MyDbHandler.LOCALE + ","
                            + MyDbHandler.SHOW_ATTENDEE_SESSIONS + ","
                            + MyDbHandler.SHOW_SCHEDULE_BUTTON + ","
                            + MyDbHandler.SHOW_MYSCHEDULE_BUTTON + ","
                            + MyDbHandler.SHOW_NOTES_BUTTON + ","

                            + MyDbHandler.NAME_ORDER + ","
                            + MyDbHandler.SORT_ORDER + ","
                            + MyDbHandler.EVENT_WEBSITE_URL + ","

                            + MyDbHandler.ENABLE_ACTIVIT_YFEED + ","
                            + MyDbHandler.ALLOWED_TOPOST_FEED + ","
                            + MyDbHandler.IS_MODERATORAVAILABLE + ","

                            + MyDbHandler.SHOWTRACKS + ","
                            + MyDbHandler.APP_VERSION + ","
                            + MyDbHandler.FORCE_DELETE + ","
                            + MyDbHandler.FORCE_UPGRADE + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

            );

            if (eData != null) {

                stmt.bindString(1, eData.getEvent_id());
                stmt.bindString(2, eData.getEvent_name());
                stmt.bindString(3, eData.getStart_date());
                stmt.bindString(4, eData.getEnd_date());

                stmt.bindString(5, eData.getDescription());
                stmt.bindString(6, eData.getImageURL());
                stmt.bindString(7, eData.getLarge_image());

                stmt.bindString(8, eData.getWeb_logo_image());
                stmt.bindString(9, eData.getStatic_map_url());

                stmt.bindString(10, eData.getAddress1());
                stmt.bindString(11, eData.getAddress2());

                stmt.bindString(12, eData.getVenue());
                stmt.bindString(13, eData.getCity());
                stmt.bindString(14, eData.getState());
                stmt.bindString(15, eData.getCountry());
                stmt.bindString(16, eData.getZipcode());

                stmt.bindString(17, eData.getTimezone());
                stmt.bindString(18, eData.getRegister_url());

                stmt.bindString(19, eData.getHashtag());
                stmt.bindString(20, eData.getLoginRequired());

                stmt.bindString(21, eData.getRegistration_required());
                stmt.bindString(22, eData.getAboutText());

                stmt.bindString(23, eData.getLattitude());
                stmt.bindString(24, eData.getLongitude());

                stmt.bindString(25, eData.getAbout_section_text());
                stmt.bindString(26, eData.getSocial_section_text());
                stmt.bindString(27, eData.getSurvey_login_req());
                stmt.bindString(28, eData.getMycalendar_login_required());
                stmt.bindString(29, eData.getMyschedule_login_enabled());

                stmt.bindString(30, eData.getHide_attendee_info());
                stmt.bindString(31, eData.getHide_speaker_info());
                stmt.bindString(32, eData.getHide_exhibitor_images());
                stmt.bindString(33, eData.getHide_sponsor_images());
                stmt.bindString(34, eData.getHide_speaker_images());
                stmt.bindString(35, eData.getHide_attendee_images());

                stmt.bindString(36, eData.getEvent_color());
                stmt.bindString(37, eData.getNotes_login_required());
                stmt.bindString(38, eData.getAttendee_login_required());

                stmt.bindString(39, eData.getLocale());
                stmt.bindString(40, eData.getShow_attendee_sessions());
                stmt.bindString(41, eData.getShow_schedule_button());
                stmt.bindString(42, eData.getShow_myschedule_button());
                stmt.bindString(43, eData.getShow_notes_button());

                stmt.bindString(44, eData.getName_order());
                stmt.bindString(45, eData.getSort_order());
                stmt.bindString(46, eData.getEvent_website_url());

                stmt.bindString(47, eData.getEnable_activity_feed());
                stmt.bindString(48, eData.getAllow_to_post_feed());
                stmt.bindString(49, eData.getPhoto_gallery_moderator_enable());

                stmt.bindString(50, eData.getShowTracks());
                stmt.bindString(51, eData.getAppVersion());
                stmt.bindString(52, eData.getForce_delete());
                stmt.bindString(53, eData.getForce_upgrade());

                stmt.execute();
                stmt.clearBindings();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        if (db != null) {
           closeDb();
        }
    }

    public String getValue(String columnName){

        openDB(0);

        String value = "";
        String query = "select "+ columnName + " from " + MyDbHandler.EVENT_TABLE;

        Cursor mCursor = null;
        try {
             mCursor = db.rawQuery(query, null);
            if(mCursor != null && mCursor.moveToFirst()){
                value =    mCursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(mCursor != null && !mCursor.isClosed()){
                mCursor.close();
            }
            closeDb();
        }

        return value;
    }

    public void deleteEventData() {

        openDB(1);
        try {
            db.delete(MyDbHandler.EVENT_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }


}
