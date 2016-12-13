package com.grupio.db;

/**
 * Created by JSN on 12/12/16.
 */

public class AdsTable {

    //table name
    public static final String ADS_TABLE = "ads_table";

    //columns
    public static final String ID = "ID";
    public static final String ADHTML = "adHtml";
    public static final String IMAGEURL = "imageUrl";
    public static final String GOTOURL = "goToUrl";
    public static final String ADSORDER = "adsOrder";
    public static final String SECTION = "sectionName";
    public static final String IMAGEURL480 = "imageUrl480";
    public static final String IMAGEURL800 = "imageUrl800";
    public static final String STATUS = "status";

    public static final String CREATE_ADS_TABLE = "CREATE TABLE IF NOT EXISTS " + ADS_TABLE + "("
            + ID + " TEXT, "
            + ADHTML + " TEXT, "
            + IMAGEURL + " TEXT, "
            + GOTOURL + " TEXT, "
            + ADSORDER + " TEXT, "
            + SECTION + " TEXT, "
            + IMAGEURL480 + " TEXT, "
            + IMAGEURL800 + " TEXT, "
            + STATUS +
            " TEXT );";

    public static final String INSERT_DATA = "INSERT INTO " + ADS_TABLE + "(" +
            ID + ","
            + ADHTML + ","
            + IMAGEURL + ","
            + GOTOURL + ","
            + ADSORDER + ","
            + SECTION + ","
            + IMAGEURL480 + ","
            + IMAGEURL800 + ","
            + STATUS
            + ") values(?,?,?,?,?,?,?,?,?);";
}
