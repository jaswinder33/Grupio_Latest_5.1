package com.grupio.session;

import android.os.Build;

import com.grupio.BuildConfig;

/**
 * Created by JSN on 19/9/16.
 */
public class ConstantData {


    public static String EVENT_ID = "151";
    public static final String ORG_ID = "1";
    public static final String SUB_ORG_ID = "";
    public static final String BASE_FOLDER = BuildConfig.BASE_FOLDER;


    /*
    sd card params
     */
    public static final String RESOURCES = "Resources";
    public static final String MAPS = "Maps";
    public static final String MENUS = "Menus";

    /*
    Resources
     */
    public static final String SPLASH="splash_image";

    /*
    API end points
     */
    public static final String BASE_URL = "https://conf.dharanet.com/conf/v1/main";
    public static final String API_FORMAT = "&format=json";
    public static final String API_EVENT = "&event_id=";

    public static final String ADS_API = BASE_URL + "/getads.php?event_id=";
    public static final String EVENT_DETAILS_API = BASE_URL + "/eventdetails.php?event_id=";
    public static final String EVENT_LIST_API = BASE_URL + "/search.php";
    public static final String EXHIBITORS_API = BASE_URL + "/exhibitors.php?event_id=";
    public static final String GRAPHICS_API = BASE_URL + "/event_graphics.php?event_id=";
    public static final String LOCALE_API = BASE_URL + "/getlanguage.php?locale=";
    public static final String LOGISTICS_API = BASE_URL + "/logistics.php?event_id=";
    public static final String MAPS_API = BASE_URL + "/maps.php?event_id=";
    public static final String MENU_API = BASE_URL + "/getmenuitems.php?event_id=";
    public static final String ATTENDEES_API = BASE_URL + "/attendees.php?event_id=";
    public static final String SESSIONS_API =BASE_URL +  "/sessions.php?event_id=";
    public static final String SPEAKERS_API =BASE_URL + "/speakers.php?event_id=";
    public static final String SPONSORS_API =BASE_URL + "/sponsors.php?event_id=";
    public static final String SURVEYS_API =BASE_URL + "/getsurveys.php?event_id=";
    public static final String VERSION_API =BASE_URL + "/getversions.php?format=json&event_id=";

}
