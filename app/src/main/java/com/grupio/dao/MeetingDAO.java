package com.grupio.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.MeetingData;
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


    public void deleteInviteTable() {

        openDB(0);
        try {
            db.delete(MeetingTable.InvitationStatus.INVITATION_STATUS_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDb();
    }
}

