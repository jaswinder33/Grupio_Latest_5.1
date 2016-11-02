package com.grupio.db;

/**
 * Created by JSN on 24/10/16.
 */

public class LogisticsTable {

    //Table name
    public static final String LOGISTICS_TABLE = "logistics";


    //fields
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String DOCTYPE = "doctype";
    public static final String TYPE = "type";
    public static final String ELEMENT_ORDER = "element_order";

    // create table
    public static final String CREATE_LOGISTICS_TABLE = "CREATE TABLE  IF NOT EXISTS " + LOGISTICS_TABLE
            + "("
            + ID + " TEXT, "
            + NAME + " TEXT, "
            + URL + " TEXT, "
            + DOCTYPE + " TEXT, "
            + TYPE + " TEXT, "
            + ELEMENT_ORDER + " TEXT"
            + ");";
}
