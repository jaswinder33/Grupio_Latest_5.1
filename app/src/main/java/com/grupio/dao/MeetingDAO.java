package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.MeetingData;
import com.grupio.db.ExhibitorLikeTable;
import com.grupio.db.SessionTable;
import com.grupio.helper.MeetingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 14/12/16.
 */

public class MeetingDAO extends BaseDAO {

    protected MeetingDAO(Context mContext) {
        super(mContext);
    }

    public static MeetingDAO getInstance(Context mContext) {
        return new MeetingDAO(mContext);
    }

    public void insertData(String response) {

        deleteMeetingTable();
        deleteInviteTable();

        List<MeetingData> mList = new ArrayList<>();

        MeetingHelper mHelper = new MeetingHelper();
        mList.addAll(mHelper.parseData(response));

        String insertQuery = "INSERT INTO " + MeetingTable.MEETING_TABLE + " (" +
                MeetingTable.ID + ","
                + MeetingTable.MEETING_TIME + ","
                + MeetingTable.MEETING_DATE + ","
                + MeetingTable.CREATOR_ID + ","
                + MeetingTable.INVITIES + ","
                + MeetingTable.CURRENTDATE + ","
                + MeetingTable.TITLE + ","
                + MeetingTable.LOCATION + ","
                + MeetingTable.DESCRIPTION + ") VALUES(?,?,?,?,?,?,?,?,?);";

        openDB(1);

        db.beginTransaction();

        try {
            SQLiteStatement stmt = db.compileStatement(insertQuery);

            if (!mList.isEmpty()) {

                for (int i = 0; i < mList.size(); i++) {
                    stmt.bindString(1, mList.get(i).id);
                    stmt.bindString(2, mList.get(i).meetingTime);
                    stmt.bindString(3, mList.get(i).meetingDate);
                    stmt.bindString(4, mList.get(i).creator);
                    stmt.bindString(5, mList.get(i).invities);
                    stmt.bindString(6, mList.get(i).currentDate);
                    stmt.bindString(7, mList.get(i).title);
                    stmt.bindString(8, mList.get(i).location);
                    stmt.bindString(9, mList.get(i).description);

                    stmt.executeInsert();
                    stmt.clearBindings();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            db.setTransactionSuccessful();
            db.endTransaction();

            closeDb();
        }

    }

    public void deleteMeetingTable() {
        openDB(0);
        try {
            db.delete(MeetingTable.MEETING_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDb();
    }


    /**
     * This method returns list of meetings on particular date.
     *
     * @param date
     * @return Sample query
     * <p>
     * date == null
     * select distinct * from meeting_table where meeting_date =(select min(start_time) from(select  min(date(sessions.start_time)) as start_time from sessions left join likes on sessions.id=likes.id where likes.isFav='1' union select  min(meeting_date) as start_time from meeting_table ) );
     * <p>
     * date != null
     * select distinct * from meeting_table where meeting_date ='2016-05-02';
     */
    public List<MeetingData> getMeetingList(String date) {
        List<MeetingData> mList = new ArrayList<>();


        MeetingHelper mHelper = new MeetingHelper();

        String query = "select distinct * from " + MeetingTable.MEETING_TABLE;//+ " where " + MeetingTable.MEETING_DATE ;

        if (date == null) {
//            query += " where " + MeetingTable.MEETING_DATE + " =(select min(" + MeetingTable.MEETING_DATE + ") from " + MeetingTable.MEETING_TABLE + " );";
            query += " where " + MeetingTable.MEETING_DATE + " =(select min(start_time) from(" +
                    "select  min(date(sessions.start_time)) as start_time from " + SessionTable.SESSION_TABLE + " left join " + ExhibitorLikeTable.LIKE_TABLE + " on sessions.id=likes.id where likes.isFav='1' union select  min(" + MeetingTable.MEETING_DATE + ") as start_time from " + MeetingTable.MEETING_TABLE +
                    " ) );";
        } else {
            query += " where " + MeetingTable.MEETING_DATE + " ='" + date + "';";
        }

        openDB(0);

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.getCount() > 0) {
                mCursor.moveToFirst();

                MeetingData meetingData;
                do {
                    meetingData = new MeetingData();
                    meetingData.id = mCursor.getString(mCursor.getColumnIndex(MeetingTable.ID));
                    meetingData.currentDate = mCursor.getString(mCursor.getColumnIndex(MeetingTable.CURRENTDATE));
                    meetingData.meetingTime = mCursor.getString(mCursor.getColumnIndex(MeetingTable.MEETING_TIME));
                    meetingData.meetingDate = mCursor.getString(mCursor.getColumnIndex(MeetingTable.MEETING_DATE));
                    meetingData.title = mCursor.getString(mCursor.getColumnIndex(MeetingTable.TITLE));
                    meetingData.location = mCursor.getString(mCursor.getColumnIndex(MeetingTable.LOCATION));
                    meetingData.description = mCursor.getString(mCursor.getColumnIndex(MeetingTable.DESCRIPTION));
                    meetingData.creator = mCursor.getString(mCursor.getColumnIndex(MeetingTable.CREATOR_ID));
                    meetingData.invities = mCursor.getString(mCursor.getColumnIndex(MeetingTable.INVITIES));

                    String[] time = mHelper.parseMeetingTime(meetingData.meetingTime);
                    meetingData.startTime = time[0];
                    meetingData.endTime = time[1];
                    mList.add(meetingData);
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
     * @return Sample query
     * <p>
     * select start_time from (
     * select distinct date(start_time) as start_time from sessions left join likes on sessions.id=likes.id where likes.isFav='1' union select distinct date(meeting_date) as start_time from meeting_table
     * )x
     * order by start_time
     */
    public List<String> getDateList() {
        List<String> mDataList = new ArrayList<>();

        openDB(0);

        String query = "select start_time from (" +
                "select distinct date(" + SessionTable.START_TIME + ") as start_time from " + SessionTable.SESSION_TABLE + "  left join " + ExhibitorLikeTable.LIKE_TABLE + " on sessions.id=likes.id where likes.isFav='1'  union select date(" + MeetingTable.MEETING_DATE + ") as start_time from " + MeetingTable.MEETING_TABLE +
                " ) x" +
                " order by start_time;";

//        String query = "Select " + MeetingTable.MEETING_DATE + " from " + MeetingTable.MEETING_TABLE + " order by " + MeetingTable.MEETING_DATE + ";";


        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.getCount() > 0) {

                mCursor.moveToFirst();

                do {
                    mDataList.add(mCursor.getString(0));
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

        return mDataList;
    }

    public void deleteInviteTable() {

        openDB(0);
        try {
//            db.delete(MeetingTable.InvitationStatus.INVITATION_STATUS_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDb();
    }
}

