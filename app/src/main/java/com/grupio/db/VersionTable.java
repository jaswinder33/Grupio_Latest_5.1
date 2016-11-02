package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class VersionTable {

    public static final String VERSION_TABLE = "versions";

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


}
