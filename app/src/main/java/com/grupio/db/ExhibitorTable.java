package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class ExhibitorTable {

    public static final String EXHIBITOR_TABLE = "exhibitors";

    //field for exhitiros table
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String LARGEIMAGE = "largeImage";
    public static final String BIO = "bio";
    public static final String WEBSITE = "website";
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub_category";
    public static final String EMAIL = "email";
    public static final String PRIMARYPHONE = "primaryPhone";
    public static final String ADDRESS = "address";
    public static final String isFav = "isFav";
    public static final String LOCATION = "location";
    public static final String SESSIONLIST = "sessionList";
    public static final String RESOURCELINKS = "resourceLinks";
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
}
