package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class MapsTable {

    public static final String MAPS_TABLE = "maps";

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


}
