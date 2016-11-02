package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class SessionTable {

    public static final String SESSION_TABLE = "sessions";

    //Session table fields
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String TRACK = "track";
    public static final String SUMMARY = "summary";
    public static final String LOCATION = "location";
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

}
