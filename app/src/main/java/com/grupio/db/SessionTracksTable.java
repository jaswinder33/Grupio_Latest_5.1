package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class SessionTracksTable {


    public static final String SESSION_TRACKS_TABLE = "session_tracks";

    //session track table fields
    public static final String ID = "id";
    public static final String TRACK = "track";
    public static final String COLOR = "color";
    public static final String CATEGORY = "category";
    public static final String TRACK_ORDER = "track_order";

    public static final String CREATE_SESSION_TRACK_TABLE = "CREATE TABLE IF NOT EXISTS " + SESSION_TRACKS_TABLE
            + " ( "
            + ID + " TEXT, "
            + TRACK + " TEXT, "
            + COLOR + " TEXT, "
            + CATEGORY + " TEXT, "
            + TRACK_ORDER + " TEXT"
            + ");";
}
