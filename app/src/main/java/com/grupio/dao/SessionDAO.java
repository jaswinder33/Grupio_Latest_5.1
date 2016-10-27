package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.ScheduleData;
import com.grupio.db.EventTable;
import com.grupio.db.SessionTable;
import com.grupio.db.SessionTracksTable;
import com.grupio.helper.ScheduleHelper;
import com.grupio.helper.SessionWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by JSN on 24/8/16.
 */
public class SessionDAO extends BaseDAO {

    /**
     * private constructor
     */
    private SessionDAO(Context mContext) {
        super(mContext);
    }


    public static SessionDAO getInstance(Context mContext) {
        return new SessionDAO(mContext);
    }


    public void insertData(String response) {

        deleteData();

        List<ScheduleData> mDatalist = new ArrayList<>();

        ScheduleHelper sHelper = new ScheduleHelper();
        mDatalist.addAll(sHelper.parseJSON(mContext, response));

        openDB(1);

        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + SessionTable.SESSION_TABLE
                    + " ( "
                    + SessionTable.ID + ","
                    + SessionTable.NAME + ","
                    + SessionTable.START_TIME + ","
                    + SessionTable.END_TIME + ","
                    + SessionTable.TRACK + ","
                    + SessionTable.SUMMARY + ","
                    + SessionTable.LOCATION + ","
                    + SessionTable.PARENT_SESSION_ID + ","
                    + SessionTable.HAS_CHILD + ","
                    + SessionTable.ATTENDEE_LIMIT + ","
                    + SessionTable.SPEAKER_LIST + ","
                    + SessionTable.SESSION_LIST +
                    ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            if (mDatalist != null && mDatalist.size() > 0) {

                for (int i = 0; i < mDatalist.size(); i++) {
                    stmt.bindString(1, mDatalist.get(i).getSession_id());
                    stmt.bindString(2, mDatalist.get(i).getName());
                    stmt.bindString(3, mDatalist.get(i).getStart_time());
                    stmt.bindString(4, mDatalist.get(i).getEnd_time());
                    stmt.bindString(5, mDatalist.get(i).getTrack());
                    stmt.bindString(6, mDatalist.get(i).getSummary());
                    stmt.bindString(7, mDatalist.get(i).getLocation());
                    stmt.bindString(8, mDatalist.get(i).getParent_session_id());
                    stmt.bindString(9, mDatalist.get(i).getHas_child());
                    stmt.bindString(10, mDatalist.get(i).getMaxSeatsAvailable());
                    stmt.bindString(11, mDatalist.get(i).getSpeakerListAsString());
                    stmt.bindString(12, mDatalist.get(i).getResourceListAsString());
                    stmt.execute();
                    stmt.clearBindings();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        if (db != null) {
            closeDb();
        }

    }

    public List<ScheduleData> fetchSessionList(String date, String trackName) {

        openDB(0);

        List<ScheduleData> mScheduleDataList = new ArrayList<>();

        Calendar currentData = null;
        String dateNow = null;

        if (date == null) {
            currentData = Calendar.getInstance();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateNow = formatter.format(currentData.getTime());

        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date dateChange = formatter.parse(date);
                SimpleDateFormat formatterNew = new SimpleDateFormat("yyyy-MM-dd");
                dateNow = formatterNew.format(dateChange);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        String[] dateArr = dateNow.split(" ");

        String query = "select sessions.*,session_tracks.color from " + SessionTable.SESSION_TABLE + " left join " + SessionTracksTable.SESSION_TRACKS_TABLE + " on  sessions.track=session_tracks.track where " +
                " case " +
                " when (select exists(select sessions.start_time from sessions where sessions.start_time  like  '" + dateArr[0] + "%' and sessions.track = '" + trackName + "' ) ) " +
                " then sessions.start_time like '" + dateArr[0] + "%' " +
                " else sessions.start_time like " +
                "   (case     " +
                "   when    " +
                "   (select exists(select sessions.start_time from sessions where date(sessions.start_time) > date(" + dateArr[0] + ") and sessions.track = '" + trackName + "') ) " +
                "   then  (select date(sessions.start_time)  || '%'    from sessions where date(sessions.start_time) > date(" + dateArr[0] + ") and sessions.track = '" + trackName + "')   " +
                "   else    (select date(sessions.start_time)  || '%'    from sessions where date(sessions.start_time) < date(" + dateArr[0] + ") and sessions.track='" + trackName + "')   end    )  " +
                " end ";

        if (!trackName.equals("")) {
            query += "and sessions.track = '" + trackName + "';";
        }

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.moveToFirst()) {

                ScheduleData sd;
                do {
                    sd = new ScheduleData();

                    sd.setSession_id(mCursor.getString(0));
                    sd.setName(mCursor.getString(1));
                    sd.setStart_time(mCursor.getString(2));
                    sd.setEnd_time(mCursor.getString(3));
                    sd.setTrack(mCursor.getString(4));
                    sd.setSummary(mCursor.getString(5));
                    sd.setLocation(mCursor.getString(6));
                    sd.setParent_session_id(mCursor.getString(7));
                    sd.setHas_child(mCursor.getString(8));
                    sd.setMaxSeatsAvailable(mCursor.getString(9));
                    sd.setSpeakerListAsString(mCursor.getString(10));
                    sd.setResourceListAsString(mCursor.getString(11));
                    sd.setColor(mCursor.getString(12) == null ? "" : mCursor.getString(12));

                    mScheduleDataList.add(sd);
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

        if (mScheduleDataList.size() > 0) {
            String startDAte = mScheduleDataList.get(0).getStart_time();

            String[] dateArray = startDAte.split(" ");

            String dateString = dateArray[0];

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dateChange = formatter.parse(dateString);
                SimpleDateFormat formatterNew = new SimpleDateFormat("dd-MM-yyyy");
                SessionWatcher.getInstance().setDate(formatterNew.format(dateChange));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        return mScheduleDataList;

    }


    public List<String> getDateList() {
        String query = "select distinct strftime('%d-%m-%Y'," + SessionTable.START_TIME + ") from " + SessionTable.SESSION_TABLE + " ";

        query += " order by date(" + SessionTable.START_TIME + ") ";

        List<String> dateslist = new ArrayList<>();

        openDB(0);
        Cursor mcursor = null;

        try {
            mcursor = db.rawQuery(query, null);

            if (mcursor != null && mcursor.moveToFirst()) {

                do {
                    dateslist.add(mcursor.getString(0));
                } while (mcursor.moveToNext());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (mcursor != null && !mcursor.isClosed()) {
                mcursor.close();
            }
            closeDb();

        }


        return dateslist;

    }

    public void deleteData() {
        openDB(1);
        try {
            db.delete(SessionTable.SESSION_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

    public ScheduleData getSessionWithId(String id) {

        boolean showTracks = EventDAO.getInstance(mContext).getValue(EventTable.SHOWTRACKS).equals("y");

        openDB(0);
        ScheduleData sd = new ScheduleData();
        String query;
        if (showTracks) {
            query = "select sessions.*, session_tracks.color " +
                    "from " + SessionTable.SESSION_TABLE + " " +
                    "left join " + SessionTracksTable.SESSION_TRACKS_TABLE + " on sessions.track = session_tracks.track where sessions.id ='" + id + "' order by name collate nocase;";
//            query = "Select * from " + SessionTable.SESSION_TABLE + " where " + SessionTable.ID + "='" + id + "';";
        } else {
            query = "Select * from " + SessionTable.SESSION_TABLE + " where " + SessionTable.ID + "='" + id + "' order by name collate nocase;";
        }

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    sd.setSession_id(mCursor.getString(0));
                    sd.setName(mCursor.getString(1));
                    sd.setStart_time(mCursor.getString(2));
                    sd.setEnd_time(mCursor.getString(3));
                    sd.setTrack(mCursor.getString(4));
                    sd.setSummary(mCursor.getString(5));
                    sd.setLocation(mCursor.getString(6));
                    sd.setParent_session_id(mCursor.getString(7));
                    sd.setHas_child(mCursor.getString(8));
                    sd.setMaxSeatsAvailable(mCursor.getString(9));
                    sd.setSpeakerListAsString(mCursor.getString(10));
                    sd.setResourceListAsString(mCursor.getString(11));

                    try {
                        sd.setColor(mCursor.getString(12));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

        return sd;
    }

}
