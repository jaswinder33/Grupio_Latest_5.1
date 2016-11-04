package com.grupio.db;

/**
 * Created by JSN on 3/11/16.
 */

public class LiveTable {

    public static final String LIVETABLE = "livetable";


    //FIELDS
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String IMAGE_URL = "image_url";

    public static final String CREATE_LIVE_TABLE = "CREATE TABLE IF NOT EXISTS " + LIVETABLE + " ( "
            + ID + " TEXT, "
            + NAME + " TEXT, "
            + URL + " TEXT, "
            + IMAGE_URL + " TEXT"
            + " );";


}
