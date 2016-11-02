package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class SpeakerTable {

    public static final String SPEAKER_TABLE = "speakers";

    //fields for speakerTable
    public static final String ID = "id";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String TITLE = "title";
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
    public static final String CATEGORY = "category";
    public static final String SESSIONLIST = "sessionList";
    public static final String RESOURCELINKS = "resourceLinks";
    public static final String ENABLECONTACTS = "enablecontacts";
    public static final String HIDECONTACTINFO = "hidecontactinfo";


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


}
