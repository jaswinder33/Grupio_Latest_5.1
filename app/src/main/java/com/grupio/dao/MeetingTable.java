package com.grupio.dao;

/**
 * Created by JSN on 14/12/16.
 */

public class MeetingTable {

    //table name
    public static final String MEETING_TABLE = "meeting_table";

    //table params
    public static final String ID = "id";
    public static final String MEETING_TIME = "meeting_time";
    public static final String MEETING_DATE = "meeting_date";
    public static final String CREATOR_ID = "creator_id";
    public static final String INVITIES = "invities";

    public static final String CURRENTDATE = "current_date";
    public static final String TITLE = "title";
    public static final String LOCATION = "location";
    public static final String DESCRIPTION = "description";

    public static final String CREATE_MEETING_TABLE = "CREATE TABLE IF NOT EXISTS " + MEETING_TABLE + " (" +
            ID + " TEXT, "
            + CURRENTDATE + " TEXT, "
            + MEETING_TIME + " TEXT, "
            + MEETING_DATE + " TEXT, "
            + TITLE + " TEXT, "
            + LOCATION + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + CREATOR_ID + " TEXT, "
            + INVITIES + " TEXT);";


    public static class InvitationStatus {
        //table name
        public static final String INVITATION_STATUS_TABLE = "invitation_status";

        //Triggers
        public static final String MEETING_INSERT_TRIGGER = "meeting_insert";
        public static final String MEETING_DELETE_TRIGGER = "meeting_delete";

        //table fields
        public static final String MEETING_ID = "meeting_id";
        public static final String ATTENDEE_ID = "attendee_id";
        public static final String STATUS = "status";

        public static final String CREATE_INVITATION_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS " + INVITATION_STATUS_TABLE + " ("
                + MEETING_ID + " TEXT, "
                + ATTENDEE_ID + " TEXT, "
                + STATUS +
                " TEXT );";

        public static final String INVITATION_TRIGGER = "CREATE TRIGGER IF NOT EXISTS " + MEETING_INSERT_TRIGGER + " AFTER INSERT ON[" + MEETING_TABLE + "] FOR EACH ROW "
                + " BEGIN "
                + "INSERT INTO " + INVITATION_STATUS_TABLE + " VALUES(new.id, new.creator_id, new.INVITIES);"
                + " END;";

        public static final String INVITATION_DELETE_TRIGGER = "CREATE TRIGGER IF NOT EXISTS " + MEETING_DELETE_TRIGGER
                + " AFTER DELETE "
                + " ON[" + MEETING_TABLE + "] "
                + " FOR EACH ROW "
                + " BEGIN "
                + " DELETE FROM " + INVITATION_STATUS_TABLE + " WHERE " + ATTENDEE_ID + "=old.attendee_id AND " + MEETING_ID + "=old.meeting_id; "
                + " END;";
    }

}
