package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.EventData;
import com.grupio.db.EventTable;
import com.grupio.helper.EventDataProcessor;


/**
 * Created by JSN on 22/8/16.
 */
public class EventDAO extends BaseDAO {

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
            stmt = db.compileStatement("INSERT INTO " + EventTable.EVENT_TABLE
                    + " ( "
                    + EventTable.EVENT_ID + ","
                    + EventTable.EVENT_NAME + ","
                    + EventTable.START_DATE + ","
                    + EventTable.END_DATE + ","

                    + EventTable.DESCRIPTION + ","
                    + EventTable.IMAGEURL + ","
                    + EventTable.LARGE_IMAGE + ","

                    + EventTable.WEB_LOGO_IMAGE + ","
                    + EventTable.STATIC_MAP_URL + ","

                    + EventTable.ADDRESS1 + ","
                    + EventTable.ADDRESS2 + ","

                    + EventTable.VENUE + ","
                    + EventTable.CITY + ","
                    + EventTable.STATE + ","
                    + EventTable.COUNTRY + ","
                    + EventTable.ZIPCODE + ","

                    + EventTable.TIMEZONE + ","
                    + EventTable.REGISTER_URL + ","

                    + EventTable.HASHTAG + ","
                    + EventTable.LOGINREQUIRED + ","

                    + EventTable.REGISTRATION_REQUIRED + ","
                    + EventTable.ABOUT_TEXT + ","
                    + EventTable.LATTITUDE + ","
                    + EventTable.LONGITUDE + ","

                    + EventTable.ABOUT_SECTION_TEXT + ","
                    + EventTable.SOCIAL_SECTION_TEXT + ","
                    + EventTable.SURVEY_LOGIN_REQ + ","
                    + EventTable.MYCALENDAR_LOGIN_REQUIRED + ","
                    + EventTable.MYSCHEDULE_LOGIN_ENABLED + ","

                    + EventTable.HIDE_ATTENDEE_INFO + ","
                    + EventTable.HIDE_SPEAKER_INFO + ","
                    + EventTable.HIDE_EXHIBITOR_IMAGES + ","
                    + EventTable.HIDE_SPONSOR_IMAGES + ","
                    + EventTable.HIDE_SPEAKER_IMAGES + ","
                    + EventTable.HIDE_ATTENDEE_IMAGES + ","

                    + EventTable.COLOR_THEME + ","
                    + EventTable.NOTES_LOGIN_REQUIRED + ","
                    + EventTable.ATTENDEE_LOGIN_REQUIRED + ","

                    + EventTable.LOCALE + ","
                    + EventTable.SHOW_ATTENDEE_SESSIONS + ","
                    + EventTable.SHOW_SCHEDULE_BUTTON + ","
                    + EventTable.SHOW_MYSCHEDULE_BUTTON + ","
                    + EventTable.SHOW_NOTES_BUTTON + ","

                    + EventTable.NAME_ORDER + ","
                    + EventTable.SORT_ORDER + ","
                    + EventTable.EVENT_WEBSITE_URL + ","

                    + EventTable.ENABLE_ACTIVIT_YFEED + ","
                    + EventTable.ALLOWED_TOPOST_FEED + ","
                    + EventTable.IS_MODERATORAVAILABLE + ","

                    + EventTable.SHOWTRACKS + ","
                    + EventTable.APP_VERSION + ","
                    + EventTable.FORCE_DELETE + ","

                    + EventTable.FORCE_UPGRADE + ","
                    + EventTable.GET_TIMEZONE + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

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
                stmt.bindString(54, eData.getGettimezone());

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

    public String getValue(String columnName) {

        openDB(0);

        String value = "";
        String query = "select " + columnName + " from " + EventTable.EVENT_TABLE;

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);
            if (mCursor != null && mCursor.moveToFirst()) {
                value = mCursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            closeDb();
        }

        return value;
    }

    public void deleteEventData() {

        openDB(1);
        try {
            db.delete(EventTable.EVENT_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }


}
