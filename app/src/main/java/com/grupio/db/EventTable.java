package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class EventTable {

    public static final String EVENT_TABLE = "event";

    //event data params
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

    public static final String GET_TIMEZONE = "get_timezone";

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
            + FORCE_UPGRADE + " TEXT, "
            + GET_TIMEZONE + " TEXT"
            + ");";


}
