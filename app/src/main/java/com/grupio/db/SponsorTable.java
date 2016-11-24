package com.grupio.db;

/**
 * Created by JSN on 21/11/16.
 */

public class SponsorTable {

    public static final String SPONSOR_TABLE = "sponsors";

    //fields
    public static final String SPONSORID = "sponsorid";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String URL = "url";
    public static final String LARGEURL = "large_url";
    public static final String WEBURL = "weburl";
    public static final String SPONSORSESSIONS = "sponsor_sessions";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String BOOTH = "booth";
    public static final String DESCRIPTION = "description";
    public static final String SUMMARY = "summary";
    public static final String SPONSORLINK = "sponsor_link";

    //create table
    public static final String CREATE_SPONSOR_TABLE = "CREATE TABLE IF NOT EXISTS " + SPONSOR_TABLE + "(" +
            SPONSORID + " TEXT, "
            + NAME + " TEXT, "
            + TYPE + " TEXT, "
            + URL + " TEXT, "
            + LARGEURL + " TEXT, "
            + WEBURL + " TEXT, "
            + SPONSORSESSIONS + " TEXT, "
            + PHONE + " TEXT, "
            + EMAIL + " TEXT, "
            + BOOTH + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + SUMMARY + " TEXT, "
            + SPONSORLINK + " TEXT"
            + ")";

}
