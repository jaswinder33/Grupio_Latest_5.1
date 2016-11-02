package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.MessageData;
import com.grupio.db.MessageTable;
import com.grupio.message.MessageDataProcessor;
import com.grupio.session.Preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 7/7/16.
 */
public class MessageDAO extends BaseDAO {

    private static MessageDAO mMessageDAO;

    /**
     * Private Constructor
     *
     * @param mContext
     */
    private MessageDAO(Context mContext) {
        super(mContext);
//        this.mContext = mContext;
    }

    /**
     * Return instance of MessageDAO
     *
     * @param mContext
     * @return
     */
    public static MessageDAO getInstance(Context mContext) {
        if (mMessageDAO == null) {
            mMessageDAO = new MessageDAO(mContext);
        }
        return mMessageDAO;
    }

    /**
     * Insert data in message table.
     *
     * @param response response in form of string.
     * @return boolean value if object inserted successfully.
     */
    public synchronized void insertData(String response) {

        List<MessageData> mData = new ArrayList<>();

        MessageDataProcessor mProcessor = new MessageDataProcessor(mContext);
        mData.addAll(mProcessor.parseData(response));

        openDB(1);
        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " +
                    MessageTable.MESSAGE_TABLE +
                    " (" +
                    MessageTable.MESSGAE_ID +
                    "," +
                    MessageTable.THREAD_ID +
                    "," +
                    MessageTable.SENDER_ID +
                    "," +
                    MessageTable.RECEIVER_ID +
                    "," +
                    MessageTable.IS_UNREAD +
                    "," +
                    MessageTable.DATETIME +
                    "," +
                    MessageTable.FOLDER +
                    "," +
                    MessageTable.TITLE +
                    "," +
                    MessageTable.CONTENT +
                    "," +
                    MessageTable.RECEIVER_EMAIL +
                    "," +
                    MessageTable.RECEIVER_FIRST_NAME +
                    "," +
                    MessageTable.RECEIVER_LAST_NAME +
                    "," +
                    MessageTable.SENDER_EMAIL +
                    "," +
                    MessageTable.SENDER_FIRST_NAME +
                    "," +
                    MessageTable.SENDER_LAST_NAME +
                    ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


            if (mData != null && !mData.isEmpty()) {

                for (int i = 0; i < mData.size(); i++) {
                    stmt.bindString(1, mData.get(i).getMessage_id());
                    stmt.bindString(2, mData.get(i).getThread_id());
                    stmt.bindString(3, mData.get(i).getSender_id());
                    stmt.bindString(4, mData.get(i).getReceiver_id());
                    stmt.bindString(5, mData.get(i).getIs_unread());
                    stmt.bindString(6, mData.get(i).getDatetime());
                    stmt.bindString(7, mData.get(i).getFolder());
                    stmt.bindString(8, mData.get(i).getTitle());
                    stmt.bindString(9, mData.get(i).getContent());
                    stmt.bindString(10, mData.get(i).getReceiver_email());
                    stmt.bindString(11, mData.get(i).getReceiver_first_name());
                    stmt.bindString(12, mData.get(i).getReceiver_last_name());
                    stmt.bindString(13, mData.get(i).getSender_email());
                    stmt.bindString(14, mData.get(i).getSender_first_name());
                    stmt.bindString(15, mData.get(i).getSender_last_name());

                    stmt.executeInsert();
                    stmt.clearBindings();
                }

                db.setTransactionSuccessful();
                db.endTransaction();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }


    }

    /**
     * Insert data in message table.
     *
     * @param mObj object on which write operation has to be performed
     * @return boolean value if object inserted successfully.
     */
    public Boolean insertData(MessageData mObj) {

        openDB(1);
        SQLiteStatement stmt = null;

        try {
            stmt = db.compileStatement("INSERT INTO " +
                    MessageTable.MESSAGE_TABLE +
                    " (" +
                    MessageTable.MESSGAE_ID +
                    "," +
                    MessageTable.THREAD_ID +
                    "," +
                    MessageTable.SENDER_ID +
                    "," +
                    MessageTable.RECEIVER_ID +
                    "," +
                    MessageTable.IS_UNREAD +
                    "," +
                    MessageTable.DATETIME +
                    "," +
                    MessageTable.FOLDER +
                    "," +
                    MessageTable.TITLE +
                    "," +
                    MessageTable.CONTENT +
                    "," +
                    MessageTable.RECEIVER_EMAIL +
                    "," +
                    MessageTable.RECEIVER_FIRST_NAME +
                    "," +
                    MessageTable.RECEIVER_LAST_NAME +
                    "," +
                    MessageTable.SENDER_EMAIL +
                    "," +
                    MessageTable.SENDER_FIRST_NAME +
                    "," +
                    MessageTable.SENDER_LAST_NAME +
                    ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.bindString(1, mObj.getMessage_id());
            stmt.bindString(2, mObj.getThread_id());
            stmt.bindString(3, mObj.getSender_id());
            stmt.bindString(4, mObj.getReceiver_id());
            stmt.bindString(5, mObj.getIs_unread());
            stmt.bindString(6, mObj.getDatetime());
            stmt.bindString(7, mObj.getFolder());
            stmt.bindString(8, mObj.getTitle());
            stmt.bindString(9, mObj.getContent());
            stmt.bindString(10, mObj.getReceiver_email());
            stmt.bindString(11, mObj.getReceiver_first_name());
            stmt.bindString(12, mObj.getReceiver_last_name());
            stmt.bindString(13, mObj.getSender_email());
            stmt.bindString(14, mObj.getSender_first_name());
            stmt.bindString(15, mObj.getSender_last_name());

            return stmt.executeInsert() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }
        return false;

    }


    /**
     * Get list of all messages that are received to logged in attendee
     *
     * @param queryStr variable used to filter results according to ender_first_name or sender_last_name.
     *                 <p>
     *                 if queryStr is null return full list otherwise return list where sender_first_name or sender_last_name starts with querytStr
     * @return list of results returned by db query.
     */
    public List<MessageData> getInboxMsgList(String queryStr) {
        openDB(0);

        List<MessageData> mList = new ArrayList<>();
        MessageData mData;

        String query;
        if (queryStr == null) {
            query = "Select * ,max(" + MessageTable.MESSGAE_ID + ")  from " + MessageTable.MESSAGE_TABLE + " where " + MessageTable.RECEIVER_ID + " ='" + Preferences.getInstances(mContext).getAttendeeId() + "'  group by thread_id order by message_id desc;";
        } else {
            query = "Select * ,max(" + MessageTable.MESSGAE_ID + ")  from " + MessageTable.MESSAGE_TABLE + " where " + MessageTable.RECEIVER_ID + " ='" + Preferences.getInstances(mContext).getAttendeeId() + "' and (" + MessageTable.SENDER_FIRST_NAME + " like '" + queryStr + "%' or " + MessageTable.SENDER_LAST_NAME + " like '" + queryStr + "%')  group by thread_id order by message_id desc;";
        }

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);
            if (mCursor != null) {
                mCursor.moveToFirst();

                do {
                    mData = new MessageData();

                    mData.setMessage_id(mCursor.getString(0));
                    mData.setThread_id(mCursor.getString(1));
                    mData.setSender_id(mCursor.getString(2));
                    mData.setReceiver_id(mCursor.getString(3));
                    mData.setIs_unread(mCursor.getString(4));
                    mData.setDatetime(mCursor.getString(5));
                    mData.setFolder(mCursor.getString(6));
                    mData.setTitle(mCursor.getString(7));
                    mData.setContent(mCursor.getString(8));
                    mData.setReceiver_email(mCursor.getString(9));
                    mData.setReceiver_first_name(mCursor.getString(10));
                    mData.setReceiver_last_name(mCursor.getString(11));
                    mData.setSender_email(mCursor.getString(12));
                    mData.setSender_first_name(mCursor.getString(13));
                    mData.setSender_last_name(mCursor.getString(14));

                    mList.add(mData);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            closeDb();
        }

        return mList;


    }

    /**
     * Get list of all messages that are sent from logged in attendee.
     *
     * @param queryStr if queryStr is null fetch list of all messages where sender id is of logged in user.
     *                 sample query
     *                 Select * ,max(message_id)  from messages where sender_id = 73515176  group by thread_id;
     *                 <p>
     *                 if qurey str is not null where sender id is of logged in user with search query from receiver_first_name or receiver_last_name.
     *                 Select * ,max(message_id)  from messages where sender_id ='73515176' and (receiver_first_name  like 'm%' or receiver_last_name like 'm%')  group by thread_id;
     *                 <p>
     *                 if queryStr is null return full list otherwise return list where receiver_first_name or receiver_last_name starts with querytStr
     * @return list of results returned by db query.
     */
    public List<MessageData> getSentMsgList(String queryStr) {
        openDB(0);

        List<MessageData> mList = new ArrayList<>();
        MessageData mData;

        String query;
        if (queryStr == null) {
            query = "Select * ,max(" + MessageTable.MESSGAE_ID + ")  from " + MessageTable.MESSAGE_TABLE + " where " + MessageTable.SENDER_ID + " = " + Preferences.getInstances(mContext).getAttendeeId() + "  group by thread_id order by message_id desc;";
        } else {
            query = "Select * ,max(" + MessageTable.MESSGAE_ID + ")  from " + MessageTable.MESSAGE_TABLE + " where " + MessageTable.SENDER_ID + " ='" + Preferences.getInstances(mContext).getAttendeeId() + "' and (" + MessageTable.RECEIVER_FIRST_NAME + " like '" + queryStr + "%' or " + MessageTable.RECEIVER_LAST_NAME + " like '" + queryStr + "%')  group by thread_id order by message_id desc;";
        }

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);
            if (mCursor != null) {
                mCursor.moveToFirst();

                do {
                    mData = new MessageData();

                    mData.setMessage_id(mCursor.getString(0));
                    mData.setThread_id(mCursor.getString(1));
                    mData.setSender_id(mCursor.getString(2));
                    mData.setReceiver_id(mCursor.getString(3));
                    mData.setIs_unread(mCursor.getString(4));
                    mData.setDatetime(mCursor.getString(5));
                    mData.setFolder(mCursor.getString(6));
                    mData.setTitle(mCursor.getString(7));
                    mData.setContent(mCursor.getString(8));
                    mData.setReceiver_email(mCursor.getString(9));
                    mData.setReceiver_first_name(mCursor.getString(10));
                    mData.setReceiver_last_name(mCursor.getString(11));
                    mData.setSender_email(mCursor.getString(12));
                    mData.setSender_first_name(mCursor.getString(13));
                    mData.setSender_last_name(mCursor.getString(14));

                    mList.add(mData);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            closeDb();
        }
        return mList;
    }

    /**
     * Retrieve list of all message belong to particular thread. Messages are message_id sorted in ascending order.
     *
     * @param threadId id to which all messages belongs.
     * @return list of results returned by db query.
     */
    public List<MessageData> getMessageListOfParticularList(String threadId) {
        openDB(0);
        MessageData mData;

        List<MessageData> mList = new ArrayList<>();


//        Select *  from messages where thread_id=10051  order by datetime asc;
        String query = "Select *  from " + MessageTable.MESSAGE_TABLE + " where " + MessageTable.THREAD_ID + " = " + threadId + " group by " + MessageTable.MESSGAE_ID + " order by " + MessageTable.MESSGAE_ID + " ASC;";

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);
            if (mCursor != null) {
                mCursor.moveToFirst();

                do {
                    mData = new MessageData();

                    mData.setMessage_id(mCursor.getString(0));
                    mData.setThread_id(mCursor.getString(1));
                    mData.setSender_id(mCursor.getString(2));
                    mData.setReceiver_id(mCursor.getString(3));
                    mData.setIs_unread(mCursor.getString(4));
                    mData.setDatetime(mCursor.getString(5));
                    mData.setFolder(mCursor.getString(6));
                    mData.setTitle(mCursor.getString(7));
                    mData.setContent(mCursor.getString(8));
                    mData.setReceiver_email(mCursor.getString(9));
                    mData.setReceiver_first_name(mCursor.getString(10));
                    mData.setReceiver_last_name(mCursor.getString(11));
                    mData.setSender_email(mCursor.getString(12));
                    mData.setSender_first_name(mCursor.getString(13));
                    mData.setSender_last_name(mCursor.getString(14));

                    mList.add(mData);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            closeDb();
        }

        return mList;

    }

    /**
     * Get list of all unread message received to logged in attendee.
     * is_unread = 1
     *
     * @param threadId id to which all messages belongs.
     * @return count of unread messages returned by db query.
     */
    public String getUnreadMsgIds(String threadId) {

        String mids = "";

        openDB(0);

        String query = "Select " + MessageTable.MESSGAE_ID + " from " + MessageTable.MESSAGE_TABLE + " where  " + MessageTable.IS_UNREAD + " = '1' and " + MessageTable.THREAD_ID + "='" + threadId + "' and " + MessageTable.RECEIVER_ID + "='" + Preferences.getInstances(mContext).getAttendeeId() + "';";

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    mids = mids + mCursor.getString(0) + ",";
                } while (mCursor.moveToNext());
                mids = mids.substring(0, mids.lastIndexOf(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (mCursor != null) {
                mCursor.close();
            }
            closeDb();

        }
        return mids;
    }

    /**
     * Update unread status in message table.
     * read_status=1
     * unread_status=0
     *
     * @param ids      : message id
     * @param threadId : thread id
     */
    public void updateUnreadMsg(String ids, String threadId) {

        openDB(1);
        Cursor mCursor = null;
        String query;
        if (ids.contains(",")) {
            query = "update messages set is_unread = '0' where is_unread=1 and thread_id='" + threadId + "' and " + MessageTable.RECEIVER_ID + "='" + Preferences.getInstances(mContext).getAttendeeId() + "';";
        } else {
            query = "update messages set is_unread = '0' where thread_id='" + threadId + "' and message_id='" + ids + "' and " + MessageTable.RECEIVER_ID + "='" + Preferences.getInstances(mContext).getAttendeeId() + "';";
        }

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {
                mCursor.moveToFirst();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            closeDb();
        }

    }

    /**
     * Delete all entries in message table
     */
    public void deleteMsgs() {
        openDB(0);
        try {
            db.delete(MessageTable.MESSAGE_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

    /**
     * Get unread count
     */
    public String getUnreadMessageCount() {
        openDB(0);

        String unreadCount = "";

        String query = "select count(" + MessageTable.IS_UNREAD + ") from " + MessageTable.MESSAGE_TABLE + " where " + MessageTable.RECEIVER_ID + "='" + Preferences.getInstances(mContext).getAttendeeId() + "' and is_unread='1';";

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    unreadCount = mCursor.getString(0);
                } while (mCursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (mCursor != null) {
                mCursor.close();
            }
            closeDb();

        }

        return unreadCount;
    }


}
