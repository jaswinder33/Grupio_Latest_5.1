package com.grupio.db;

/**
 * Created by JSN on 3/11/16.
 */

public class SurveyTable {

    public static final String SURVEY_TABLE = "survey_table";

    public static final String NAME = "name";
    public static final String URL = "url";

    public static final String CREATE_SURVEY_TABLE = "CREATE TABLE IF NOT EXISTS " + SURVEY_TABLE
            + "("
            + NAME + " TEXT, "
            + URL + " TEXT "
            + ");";

}
