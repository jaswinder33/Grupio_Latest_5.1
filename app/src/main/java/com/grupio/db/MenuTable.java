package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class MenuTable {

    public static final String MENU_TABLE = "menus";

    //MENU TABLE fields
    public static final String MENU_NAME = "menu_name";
    public static final String MENU_ORDER = "menu_order";
    public static final String DISPLAY_NAME = "display_name";
    public static final String ICON_URL = "icon_url";

    public static final String CREATE_MENU_TABLE = "CREATE TABLE IF NOT EXISTS " + MENU_TABLE
            + " ( "
            + MENU_NAME + " TEXT, "
            + MENU_ORDER + " TEXT, "
            + DISPLAY_NAME + " TEXT, "
            + ICON_URL + " TEXT "
            + " ); ";


}
