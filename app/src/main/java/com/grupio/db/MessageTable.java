package com.grupio.db;

/**
 * Created by JSN on 4/10/16.
 */

public class MessageTable {

    public static final String MESSAGE_TABLE = "messages";

    //fields for Message Table
    public static final String MESSGAE_ID = "message_id";
    public static final String THREAD_ID = "thread_id";
    public static final String SENDER_ID = "sender_id";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String IS_UNREAD = "is_unread";
    public static final String DATETIME = "datetime";
    public static final String FOLDER = "folder";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String RECEIVER_EMAIL = "receiver_email";
    public static final String RECEIVER_FIRST_NAME = "receiver_first_name ";
    public static final String RECEIVER_LAST_NAME = "receiver_last_name";
    public static final String SENDER_EMAIL = "sender_email";
    public static final String SENDER_FIRST_NAME = "sender_first_name";
    public static final String SENDER_LAST_NAME = "sender_last_name";

    public static final String CREATE_MSG_TABLE = "CREATE TABLE IF NOT EXISTS " + MESSAGE_TABLE
            + " ("
            + MESSGAE_ID + " text, "
            + THREAD_ID + " text,  "
            + SENDER_ID + " text,  "
            + RECEIVER_ID + " text,  "
            + IS_UNREAD + " text,  "
            + DATETIME + " text,  "
            + FOLDER + " text,  "
            + TITLE + " text,  "
            + CONTENT + " text,  "
            + RECEIVER_EMAIL + " text,  "
            + RECEIVER_FIRST_NAME + " text,  "
            + RECEIVER_LAST_NAME + " text,  "
            + SENDER_EMAIL + " text,  "
            + SENDER_FIRST_NAME + " text,  "
            + SENDER_LAST_NAME + " text );  ";
}
