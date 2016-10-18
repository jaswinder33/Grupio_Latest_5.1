package com.grupio.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jsn on 16/5/16.
 */
public class MyDbHandler extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "grupio.db";
    private static final int DATABASE_VERSION = 1;

    //table name
    public static final String SESSION_NOTES_TABLE = "session_notes";
    public static final String MESSAGE_TABLE = "messages";
    public static final String ATTENDEE_TABLE = "attendees";
    public static final String SPEAKER_TABLE = "speakers";
    public static final String EXHIBITOR_TABLE = "exhibitors";
    public static final String LIKE_TABLE = "likes";
    public static final String MENU_TABLE = "menus";
    public static final String VERSION_TABLE = "versions";
    public static final String SESSION_TABLE = "sessions";
    public static final String SESSION_TRACKS_TABLE = "session_tracks";
    public static final String EVENT_TABLE = "event";
    public static final String MAPS_TABLE = "maps";
    public static final String SURVEY_TABLE = "surveys";

    //fields
    public static final String NOTE_ID = "note_id";            // text
    public static final String NOTE_TYPE = "note_type";            // text 2-session_note
    public static final String NOTE_TEXT = "note_text";            // text
    public static final String LAST_OPERATION = "last_operation";    // text, 0-add, 1- update, 2-delete , Default - blank
    public static final String NOTE_SYNC = "note_syn_require";    // text, 0-synced, 1- not synced, default = 0


    public static final String CREATE_SESSION_NOTES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + SESSION_NOTES_TABLE + " (" +
                    NOTE_ID + " TEXT, "
                    + NOTE_TYPE + " TEXT NOT NULL, "
                    + NOTE_TEXT + " TEXT, " +
                    LAST_OPERATION + " TEXT, "
                    + NOTE_SYNC + " TEXT NOT NULL DEFAULT 0 );";

    //fields for Message Table
    public static final String MESSGAE_ID = "message_id";
    public static final String THREAD_ID = "thread_id";
    public static final String SENDER_ID = "sender_id";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String IS_UNREAD = "is_unread";
    public static final String DATETIME = "datetime";
    public static final String FOLDER = "folder";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String RECEIVER_EMAIL = "receiver_email";
    public static final String RECEIVER_FIRST_NAME = "receiver_first_name ";
    public static final String RECEIVER_LAST_NAME = "receiver_last_name";
    public static final String SENDER_EMAIL = "sender_email";
    public static final String SENDER_FIRST_NAME = "sender_first_name";
    public static final String SENDER_LAST_NAME = "sender_last_name";

    public static final String CREATE_MSG_TABLE = "CREATE TABLE IF NOT EXISTS " + MESSAGE_TABLE
            + " ("
            + MESSGAE_ID + " text, "
            + THREAD_ID + " text,  "
            + SENDER_ID + " text,  "
            + RECEIVER_ID + " text,  "
            + IS_UNREAD + " text,  "
            + DATETIME + " text,  "
            + FOLDER + " text,  "
            + TITLE + " text,  "
            + CONTENT + " text,  "
            + RECEIVER_EMAIL + " text,  "
            + RECEIVER_FIRST_NAME + " text,  "
            + RECEIVER_LAST_NAME + " text,  "
            + SENDER_EMAIL + " text,  "
            + SENDER_FIRST_NAME + " text,  "
            + SENDER_LAST_NAME + " text );  ";

    //field for attendee table
    public static final String ID = "id";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    //  public static final String TITLE = "title";
    public static final String COMPANY = "company";
    public static final String EMAIL = "email";
    public static final String TYPE = "type";
    public static final String PRIMARYPHONE = "primaryPhone";
    public static final String SECONDARYPHONE = "secondaryPhone";
    public static final String IMAGE = "image";
    public static final String LARGEIMAGE = "largeImage";
    public static final String BIO = "bio";
    public static final String LINKEDINID = "linkedinId";
    public static final String TWITTERID = "twitterId";
    public static final String WEBSITE = "website";
    public static final String INTEREST = "interest";
    public static final String CATEGORY = "category";
    public static final String KEYWORDS = "keywords";
    public static final String SESSIONLIST = "sessionList";
    public static final String RESOURCELINKS = "resourceLinks";
    public static final String ENABLECONTACTS = "enablecontacts";
    public static final String ENABLEMESSAGING = "enablemessaging";
    public static final String HIDECONTACTINFO = "hidecontactinfo";
    public static final String ISADMIN = "isadmin";
    public static final String ENABLEMATCH = "enablematch";

    public static final String CREATE_ATTENDEE_TABLE = "CREATE TABLE IF NOT EXISTS " + ATTENDEE_TABLE
            + " ( "
            + ID + " TEXT, "
            + FIRSTNAME + " TEXT, "
            + LASTNAME + " TEXT, "
            + COMPANY + " TEXT, "
            + TITLE + " Text, "
            + EMAIL + " TEXT, "
            + TYPE + " TEXT, "
            + PRIMARYPHONE + " TEXT, "
            + SECONDARYPHONE + " TEXT, "
            + IMAGE + " TEXT, "
            + LARGEIMAGE + " TEXT, "
            + BIO + " TEXT, "
            + LINKEDINID + " TEXT, "
            + TWITTERID + " TEXT, "
            + WEBSITE + " TEXT, "
            + INTEREST + " TEXT, "
            + CATEGORY + " TEXT, "
            + KEYWORDS + " TEXT, "
            + SESSIONLIST + " TEXT, "
            + RESOURCELINKS + " TEXT, "
            + ENABLECONTACTS + " TEXT, "
            + ENABLEMESSAGING + " TEXT, "
            + HIDECONTACTINFO + " TEXT, "
            + ISADMIN + " TEXT, "
            + ENABLEMATCH + " TEXT);";

    //fields for speakerTable
//    public static final String ID = "id";
//    public static final String FIRSTNAME = "firstName";
//    public static final String LASTNAME = "lastName";
//    public static final String TITLE = "title";
//    public static final String COMPANY = "company";
//    public static final String EMAIL = "email";
//    public static final String TYPE = "type";
//    public static final String PRIMARYPHONE = "primaryPhone";
//    public static final String SECONDARYPHONE = "secondaryPhone";
//    public static final String IMAGE = "image";
//    public static final String LARGEIMAGE = "largeImage";
//    public static final String BIO = "bio";
//    public static final String LINKEDINID = "linkedinId";
//    public static final String TWITTERID = "twitterId";
//    public static final String WEBSITE = "website";
//    public static final String CATEGORY = "category";
//    public static final String SESSIONLIST = "sessionList";
//    public static final String RESOURCELINKS = "resourceLinks";
//    public static final String ENABLECONTACTS = "enablecontacts";
//    public static final String HIDECONTACTINFO = "hidecontactinfo";


    public static final String CREATE_SPEAKER_TABLE = "CREATE TABLE IF NOT EXISTS " + SPEAKER_TABLE
            + " ( "
            + ID + " TEXT, "
            + FIRSTNAME + " TEXT, "
            + LASTNAME + " TEXT, "
            + COMPANY + " TEXT, "
            + TITLE + " Text, "
            + EMAIL + " TEXT, "
            + TYPE + " TEXT, "
            + PRIMARYPHONE + " TEXT, "
            + SECONDARYPHONE + " TEXT, "
            + IMAGE + " TEXT, "
            + LARGEIMAGE + " TEXT, "
            + BIO + " TEXT, "
            + LINKEDINID + " TEXT, "
            + TWITTERID + " TEXT, "
            + WEBSITE + " TEXT, "
            + CATEGORY + " TEXT, "
            + SESSIONLIST + " TEXT, "
            + RESOURCELINKS + " TEXT, "
            + ENABLECONTACTS + " TEXT, "
            + HIDECONTACTINFO + " TEXT);";

    //field for exhitiros table
//    public static final String ID = "id";
    public static final String NAME = "name";
    //    public static final String IMAGE = "image";
//    public static final String LARGEIMAGE = "largeImage";
//    public static final String BIO = "bio";
//    public static final String WEBSITE = "website";
//    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub_category";
    //    public static final String EMAIL = "email";
//    public static final String PRIMARYPHONE = "primaryPhone";
    public static final String ADDRESS = "address";
    public static final String isFav = "isFav";
    public static final String LOCATION = "location";
    //    public static final String SESSIONLIST = "sessionList";
//    public static final String RESOURCELINKS = "resourceLinks";
    public static final String ATTENDEELIST = "attendeelist";


    public static final String CREATE_EXHIBITOR_TABLE = "CREATE TABLE IF NOT EXISTS " + EXHIBITOR_TABLE
            + " ( "
            + ID + " TEXT, "
            + NAME + " TEXT, "
            + IMAGE + " TEXT, "
            + LARGEIMAGE + " TEXT, "
            + BIO + " TEXT, "
            + WEBSITE + " TEXT, "
            + CATEGORY + " TEXT, "
            + SUB_CATEGORY + " TEXT, "
            + EMAIL + " TEXT, "
            + PRIMARYPHONE + " TEXT, "
            + ADDRESS + " TEXT, "
            + isFav + " TEXT, "
            + LOCATION + " TEXT, "
            + ATTENDEELIST + " TEXT, "
            + SESSIONLIST + " TEXT, "
            + RESOURCELINKS + " TEXT);";

    //exhibitor like table fields
    //    public static final String ID = "id";
//    public static final boolean isFav = false;

    public static final String CREATE_EXHIBITOR_LIKE_TABLE = "CREATE TABLE IF NOT EXISTS " + LIKE_TABLE
            + " ( "
            + ID + " TEXT, "
            + isFav + " TEXT);";


    //MENU TABLE fields
    public static final String MENU_NAME = "menu_name";
    public static final String MENU_ORDER = "menu_order";
    public static final String DISPLAY_NAME = "display_name";
    public static final String ICON_URL = "icon_url";

    public static final String CREATE_MENU_TABLE = "CREATE TABLE IF NOT EXISTS " + MENU_TABLE
            + " ( "
            + MENU_NAME + " TEXT, "
            + MENU_ORDER + " TEXT, "
            + DISPLAY_NAME + " TEXT, "
            + ICON_URL + " TEXT "
            + " ); ";

    // version table fields
    public static final String VERSION_NAME = "name";
    public static final String OLD_VERSION = "old_version";
    public static final String NEW_VERSION = "new_version";


    public static final String CREATE_VERSION_TABLE = "CREATE TABLE IF NOT EXISTS " + VERSION_TABLE
            + " ( "
            + VERSION_NAME + " TEXT, "
            + OLD_VERSION + " TEXT, "
            + NEW_VERSION + " TEXT "
            + " );";


    //event data params
//    public static final String
    public static final String EVENT_ID = "event_id";

    public static final String EVENT_NAME = "event_name";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String DESCRIPTION = "description";
    public static final String IMAGEURL = "imageURL";
    public static final String LARGE_IMAGE = "large_image";

    public static final String WEB_LOGO_IMAGE = "web_logo_image";
    public static final String STATIC_MAP_URL = "static_map_url";

    public static final String ADDRESS1 = "address1";
    public static final String ADDRESS2 = "address2";
    public static final String VENUE = "venue";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String ZIPCODE = "zipcode";

    public static final String TIMEZONE = "timezone";
    public static final String REGISTER_URL = "register_url";
    public static final String HASHTAG = "hashtag";
    public static final String LOGINREQUIRED = "loginRequired";

    public static final String REGISTRATION_REQUIRED = "registration_required";
    public static final String ABOUT_TEXT = "about_text";

    public static final String LATTITUDE = "lattitude";
    public static final String LONGITUDE = "longitude";

    public static final String ABOUT_SECTION_TEXT = "about_section_text";
    public static final String SOCIAL_SECTION_TEXT = "social_section_text";

    public static final String SURVEY_LOGIN_REQ = "survey_login_req";
    public static final String MYCALENDAR_LOGIN_REQUIRED = "mycalendar_login_required";
    public static final String MYSCHEDULE_LOGIN_ENABLED = "myschedule_login_enabled";

    public static final String HIDE_ATTENDEE_INFO = "hide_attendee_info";
    public static final String HIDE_SPEAKER_INFO = "hide_speaker_info";

    public static final String HIDE_EXHIBITOR_IMAGES = "hide_exhibitor_images";
    public static final String HIDE_SPONSOR_IMAGES = "hide_sponsor_images";
    public static final String HIDE_SPEAKER_IMAGES = "hide_speaker_images";
    public static final String HIDE_ATTENDEE_IMAGES = "hide_attendee_images";

    public static final String COLOR_THEME = "color_theme";
    public static final String NOTES_LOGIN_REQUIRED = "notes_login_required";
    public static final String ATTENDEE_LOGIN_REQUIRED = "attendee_login_required";
    public static final String LOCALE = "locale";
    public static final String SHOW_ATTENDEE_SESSIONS = "show_attendee_sessions";
    public static final String SHOW_SCHEDULE_BUTTON = "show_schedule_button";
    public static final String SHOW_MYSCHEDULE_BUTTON = "show_myschedule_button";

    public static final String SHOW_NOTES_BUTTON = "show_notes_button";
    public static final String NAME_ORDER = "name_order";
    public static final String SORT_ORDER = "sort_order";

    public static final String EVENT_WEBSITE_URL = "event_website_url";
    public static final String ENABLE_ACTIVIT_YFEED = "enable_Activity_Feed";
    public static final String ALLOWED_TOPOST_FEED = "allowedToPostFeed";
    public static final String IS_MODERATORAVAILABLE = "isModeratorAvailable";

    public static final String SHOWTRACKS = "showTracks";
    public static final String APP_VERSION = "app_version";
    public static final String FORCE_DELETE = "force_delete";
    public static final String FORCE_UPGRADE = "force_upgrade";

    public static final String CREATE_EVENT_TABLE = "CREATE TABLE IF NOT EXISTS " + EVENT_TABLE +
            " ( "
            + EVENT_ID + " TEXT, "
            + EVENT_NAME + " TEXT, "
            + START_DATE + " TEXT, "
            + END_DATE + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + IMAGEURL + " TEXT, "
            + LARGE_IMAGE + " TEXT, "
            + WEB_LOGO_IMAGE + " TEXT, "
            + STATIC_MAP_URL + " TEXT, "
            + ADDRESS1 + " TEXT, "
            + ADDRESS2 + " TEXT, "
            + VENUE + " TEXT, "
            + CITY + " TEXT, "
            + STATE + " TEXT, "
            + COUNTRY + " TEXT, "
            + ZIPCODE + " TEXT, "
            + TIMEZONE + " TEXT, "
            + REGISTER_URL + " TEXT, "
            + HASHTAG + " TEXT, "
            + LOGINREQUIRED + " TEXT, "
            + REGISTRATION_REQUIRED + " TEXT, "
            + ABOUT_TEXT + " TEXT, "
            + LATTITUDE + " TEXT, "
            + LONGITUDE + " TEXT, "
            + ABOUT_SECTION_TEXT + " TEXT, "
            + SOCIAL_SECTION_TEXT + " TEXT, "
            + SURVEY_LOGIN_REQ + " TEXT, "
            + MYCALENDAR_LOGIN_REQUIRED + " TEXT, "
            + MYSCHEDULE_LOGIN_ENABLED + " TEXT, "
            + HIDE_ATTENDEE_INFO + " TEXT, "
            + HIDE_SPEAKER_INFO + " TEXT, "
            + HIDE_EXHIBITOR_IMAGES + " TEXT, "
            + HIDE_SPONSOR_IMAGES + " TEXT, "
            + HIDE_SPEAKER_IMAGES + " TEXT, "
            + HIDE_ATTENDEE_IMAGES + " TEXT, "
            + COLOR_THEME + " TEXT, "
            + NOTES_LOGIN_REQUIRED + " TEXT, "
            + ATTENDEE_LOGIN_REQUIRED + " TEXT, "
            + LOCALE + " TEXT, "
            + SHOW_ATTENDEE_SESSIONS + " TEXT, "
            + SHOW_SCHEDULE_BUTTON + " TEXT, "
            + SHOW_MYSCHEDULE_BUTTON + " TEXT, "
            + SHOW_NOTES_BUTTON + " TEXT, "
            + NAME_ORDER + " TEXT, "
            + SORT_ORDER + " TEXT, "
            + EVENT_WEBSITE_URL + " TEXT, "
            + ENABLE_ACTIVIT_YFEED + " TEXT, "
            + ALLOWED_TOPOST_FEED + " TEXT, "
            + IS_MODERATORAVAILABLE + " TEXT, "
            + SHOWTRACKS + " TEXT, "
            + APP_VERSION + " TEXT, "
            + FORCE_DELETE + " TEXT, "
            + FORCE_UPGRADE + " TEXT"
            + ");";

    //Session table fields
//    public static final String ID="id";
//    public static final String NAME="name";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String TRACK = "track";
    public static final String SUMMARY = "summary";
    //    public static final String LOCATION="location";
    public static final String PARENT_SESSION_ID = "parent_session_id";
    public static final String HAS_CHILD = "has_child";
    public static final String ATTENDEE_LIMIT = "attendee_limit";
    public static final String SPEAKER_LIST = "speaker_list";
    public static final String SESSION_LIST = "session_list";

    public static final String CREATE_SESSION_TABLE = "CREATE TABLE IF NOT EXISTS " + SESSION_TABLE
            + " ( "
            + ID + " TEXT, "
            + NAME + " TEXT, "
            + START_TIME + " TEXT, "
            + END_TIME + " TEXT, "
            + TRACK + " TEXT, "
            + SUMMARY + " TEXT, "
            + LOCATION + " TEXT, "
            + PARENT_SESSION_ID + " TEXT, "
            + HAS_CHILD + " TEXT, "
            + ATTENDEE_LIMIT + " TEXT, "
            + SPEAKER_LIST + " TEXT, "
            + SESSION_LIST + " TEXT"
            + " );";

    //session track table fields
//    public static final String ID="id";
//    public static final String TRACK="track";
    public static final String COLOR = "color";
    //    public static final String CATEGORY="category";
    public static final String TRACK_ORDER = "track_order";

    public static final String CREATE_SESSION_TRACK_TABLE = "CREATE TABLE IF NOT EXISTS " + SESSION_TRACKS_TABLE
            + " ( "
            + ID + " TEXT, "
            + TRACK + " TEXT, "
            + COLOR + " TEXT, "
            + CATEGORY + " TEXT, "
            + TRACK_ORDER + " TEXT"
            + ");";

    //map table fields
    public static final String MAP_ID = "map_id";
    public static final String MAP_NAME = "map_name";
    public static final String MAP_URL = "map_url";
    public static final String INTERACTIVE = "interactive";
    public static final String MAP_ORDER = "map_order";

    public static final String CREATE_MAPS_TABLE = "CREATE TABLE IF NOT EXISTS " + MAPS_TABLE
            + "( "
            + MAP_ID + " TEXT, "
            + MAP_NAME + " TEXT, "
            + MAP_URL + " TEXT, "
            + INTERACTIVE + " TEXT, "
            + MAP_ORDER + " TEXT"
            + ");";


    private MyDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static MyDbHandler mMyDbHandler;

    public static MyDbHandler getInstance(Context context) {
        if (mMyDbHandler == null) {
            synchronized (MyDbHandler.class) {
                if (mMyDbHandler == null) {
                    mMyDbHandler = new MyDbHandler(context);
                }
            }
        }
        return mMyDbHandler;//= new MyDbHandler(context);
    }


    private AtomicInteger mOpenCounter = new AtomicInteger();

    SQLiteDatabase db = null;

    public synchronized SQLiteDatabase getDBObject(int isWrtitable) {

//        synchronized (MyDbHandler.class) {
            if (mOpenCounter.incrementAndGet() == 1) {
                db = (isWrtitable == 1) ? getWritableDatabase() : getReadableDatabase();
            }

            return db;
//        }


    }

    public synchronized void closeDb() {

//        synchronized (MyDbHandler.class) {
            if (mOpenCounter.decrementAndGet() == 0) {
                db.close();
            }
//        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SESSION_NOTES_TABLE);
        db.execSQL(CREATE_MSG_TABLE);
        db.execSQL(CREATE_ATTENDEE_TABLE);
        db.execSQL(CREATE_SPEAKER_TABLE);
        db.execSQL(CREATE_EXHIBITOR_TABLE);
        db.execSQL(CREATE_EXHIBITOR_LIKE_TABLE);
        db.execSQL(CREATE_MENU_TABLE);
        db.execSQL(CREATE_VERSION_TABLE);
        db.execSQL(CREATE_SESSION_TABLE);
        db.execSQL(CREATE_SESSION_TRACK_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
        db.execSQL(CREATE_MAPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
