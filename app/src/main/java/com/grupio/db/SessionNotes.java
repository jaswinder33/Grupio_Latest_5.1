package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class SessionNotes {

    public static final String SESSION_NOTES_TABLE = "session_notes";

    //fields
    public static final String NOTE_ID = "note_id";            // text
    public static final String NOTE_TYPE = "note_type";            // text 2-session_note
    public static final String NOTE_TEXT = "note_text";            // text
    public static final String LAST_OPERATION = "last_operation";    // text, 0-add, 1- update, 2-delete , Default - blank
    public static final String NOTE_SYNC = "note_syn_require";    // text, 0-synced, 1- not synced, default = 0


    public static final String CREATE_SESSION_NOTES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + SESSION_NOTES_TABLE + " (" +
                    NOTE_ID + " TEXT, "
                    + NOTE_TYPE + " TEXT NOT NULL, "
                    + NOTE_TEXT + " TEXT, " +
                    LAST_OPERATION + " TEXT, "
                    + NOTE_SYNC + " TEXT NOT NULL DEFAULT 0 );";


}
