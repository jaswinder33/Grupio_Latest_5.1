package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class NotesTable {

    public static final String NOTES_TABLE = "notes";

    //fields
    public static final String ID = "id";
    public static final String NOTE_ID = "note_id";            // text
    public static final String NOTE_TYPE = "note_type";            // text 2-session_note
    public static final String NOTE_TEXT = "note_text";            // text
    public static final String REMINDER = "reminder";
    public static final String NOTE_DATE = "note_date";
    public static final String LAST_OPERATION = "last_operation";    // text, 0-add, 1- update, 2-delete , Default - blank
    public static final String NOTE_SYNC = "note_syn_require";    // text, 0-synced, 1- not synced, default = 0


    public static final String CREATE_NOTES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + NOTES_TABLE + " (" +
                    ID + " TEXT DEFAULT '0', "
                    + NOTE_ID + " TEXT DEFAULT '0', "
                    + NOTE_TYPE + " TEXT NOT NULL, "
                    + NOTE_TEXT + " TEXT, "
                    + REMINDER + " TEXT DEFAULT '0', "
                    + NOTE_DATE + " TEXT, "
                    + LAST_OPERATION + " TEXT, "
                    + NOTE_SYNC + " TEXT NOT NULL DEFAULT 0 );";


}
