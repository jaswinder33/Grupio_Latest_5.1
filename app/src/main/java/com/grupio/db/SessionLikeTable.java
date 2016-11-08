package com.grupio.db;

/**
 * Created by JSN on 7/11/16.
 */

public class SessionLikeTable {

    public static final String LIKE_TABLE = "likes";

    //exhibitor like table fields
    public static final String ID = "id";
    public static final String isFav = "isFav";

    public static final String CREATE_EXHIBITOR_LIKE_TABLE = "CREATE TABLE IF NOT EXISTS " + LIKE_TABLE
            + " ( "
            + ID + " TEXT, "
            + isFav + " TEXT);";

}
