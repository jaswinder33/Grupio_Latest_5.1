package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.ScheduleData;
import com.grupio.db.EventTable;
import com.grupio.db.ExhibitorLikeTable;
import com.grupio.db.SessionTable;
import com.grupio.db.SessionTracksTable;
import com.grupio.helper.ScheduleHelper;

import java.util.ArrayList;
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

    public List<ScheduleData> fetchSessionList(String date, String trackName, String searchQuery) {

        String name_order = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER);
        boolean isFirstName = name_order.equals("firstname_lastname");

        openDB(0);

        List<ScheduleData> mScheduleDataList = new ArrayList<>();

        String query = "";

        query = "select sessions.*,likes.isFav,likes.calendarId, session_tracks.color from sessions left join likes on sessions.id=likes.id left join session_tracks on sessions.track = session_tracks.track";// where sessions.start_time like " + date + "%'";

        if (trackName != null) {
            if (date == null) {
                query += " where sessions.start_time=(select min(sessions.start_time) from sessions where sessions.track='" + trackName + "' and " + SessionTable.PARENT_SESSION_ID + " = '0')";
            } else {
                query += " where sessions.start_time like '" + date + "%'";
            }
        } else {
            if (date == null) {
                query += " where sessions.start_time like (select min(date(sessions.start_time)) || '%' from sessions)";
            } else {
                query += " where sessions.start_time like '" + date + "%'";
            }
        }

        if (searchQuery != null) {
            query += " and sessions.name like '" + searchQuery + "%'";
        }

        query += " order by sessions.start_time;";

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
                    if (!sd.getParent_session_id().equals("0")) {
                        continue;
                    }

                    sd.setHas_child(mCursor.getString(8));
                    sd.setMaxSeatsAvailable(mCursor.getString(9));

                    sd.setSpeakerListAsString(mCursor.getString(10));
                    sd.setResourceListAsString(mCursor.getString(11));
                    sd.setSessionFav(mCursor.getString(12) != null && mCursor.getString(12).equals("1"));
                    sd.setCalenderAddId(mCursor.getString(13));

                    sd.setColor(mCursor.getString(14) != null ? mCursor.getString(14) : "#");

                    sd.setSpeakerNameAsString(SpeakerDAO.getInstance(mContext).getSpeakerNames(mCursor.getString(10), db, isFirstName));


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

        return mScheduleDataList;

    }

    public List<String> getDateList(String trackName) {

        String query = "select distinct date(" + SessionTable.START_TIME + ") from " + SessionTable.SESSION_TABLE + " ";

        if (trackName != null) {
            query += " where " + SessionTable.TRACK + "='" + trackName + "'";
            query += " and date(" + SessionTable.START_TIME + ") not in (select  date(" + SessionTable.START_TIME + ") from " + SessionTable.SESSION_TABLE + " where parent_session_id != 0)";
        } else {
            query += " where date(" + SessionTable.START_TIME + ") not in (select  date(" + SessionTable.START_TIME + ") from " + SessionTable.SESSION_TABLE + " where parent_session_id != 0)";
        }

        query += " order by " + SessionTable.START_TIME;

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

    public String getNextDate(String date) {

        String dateStr = null;

        openDB(0);

        String query = "";

        if (date == null) {
            query = "select distinct strftime('%d-%m-%Y',  " + SessionTable.START_TIME + "  ) from sessions limit 1;";

        } else {
            query = "select strftime('%d-%m-%Y',  " + SessionTable.START_TIME + " ) from sessions where start_time > '" + date + "' limit 1;";
        }

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
                do {
                    dateStr = mCursor.getString(0);
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

        return dateStr;
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
            query = "select sessions.*,likes.isFav,likes.calendarId, session_tracks.color " +
                    " from " + SessionTable.SESSION_TABLE + " " +
                    " left join " + ExhibitorLikeTable.LIKE_TABLE + " on sessions.id=likes.id " +
                    " left join " + SessionTracksTable.SESSION_TRACKS_TABLE + " on sessions.track = session_tracks.track where sessions.id ='" + id + "' order by name collate nocase;";
        } else {

            query = "select sessions.*,likes.isFav,likes.calendarId "
                    + " from " + SessionTable.SESSION_TABLE
                    + " left join " + ExhibitorLikeTable.LIKE_TABLE + " on sessions.id=likes.id "
                    + "where sessions.id ='" + id + "';";

//            query = "Select * from " + SessionTable.SESSION_TABLE + " where " + SessionTable.ID + "='" + id + "' order by name collate nocase;";
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
                    sd.setSessionFav(mCursor.getString(12) != null && mCursor.getString(12).equals("1"));
                    sd.setCalenderAddId(mCursor.getString(13));

                    try {
                        sd.setColor(mCursor.getString(14));
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


    public void likeUnlikeSession(String sessionId, int operation) {
        openDB(1);

        SQLiteStatement stmt;

        if (operation == 1) {

            stmt = db.compileStatement("INSERT INTO " + ExhibitorLikeTable.LIKE_TABLE
                    + " ( "
                    + ExhibitorLikeTable.ID + ","
                    + ExhibitorLikeTable.isFav + ") VALUES(?,?)");

            stmt.bindString(1, sessionId);
            stmt.bindString(2, "1");
            stmt.execute();

        } else {
            try {
                db.delete(ExhibitorLikeTable.LIKE_TABLE, ExhibitorLikeTable.ID + "=?", new String[]{sessionId});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDb();
    }

    public void persistCalendarId(String eventId, String sessionId) {
        openDB(1);

        String query = "update " + ExhibitorLikeTable.LIKE_TABLE + " set " + ExhibitorLikeTable.CALENDARID + "='" + eventId + "' where " + ExhibitorLikeTable.ID + "='" + sessionId + "';";

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {
                mCursor.moveToFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
        }
        closeDb();
    }

    /**
     * Return list of all child sessions of provided parent session id
     *
     * @param parentId
     */
    public List<ScheduleData> getChildSessions(String parentId) {

        String name_order = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER);
        boolean isFirstName = name_order.equals("firstname_lastname");
        openDB(0);
        List<ScheduleData> mScheduleDataList = new ArrayList<>();

        String query = "select sessions.*,likes.isFav,likes.calendarId, session_tracks.color from sessions left join likes on sessions.id=likes.id left join session_tracks on sessions.track = session_tracks.track where sessions.parent_session_id='" + parentId + "' order by sessions.start_time;";

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
                    sd.setSessionFav(mCursor.getString(12) != null && mCursor.getString(12).equals("1"));
                    sd.setCalenderAddId(mCursor.getString(13));

                    sd.setColor(mCursor.getString(14) != null ? mCursor.getString(14) : "#");

                    sd.setSpeakerNameAsString(SpeakerDAO.getInstance(mContext).getSpeakerNames(mCursor.getString(10), db, isFirstName));

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

        return mScheduleDataList;

    }

    public List<ScheduleData> searchSessions(String queryStr, boolean isFirstName) {

        openDB(0);

        List<ScheduleData> mScheduleDataList = new ArrayList<>();

        String query = "select sessions.*,likes.isFav,likes.calendarId, session_tracks.color from sessions left join likes on sessions.id=likes.id left join session_tracks on sessions.track = session_tracks.track where sessions.name like '" + queryStr + "%' order by sessions.name COLLATE NOCASE;";// where sessions.start_time like " + date + "%'";

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
                    if (!sd.getParent_session_id().equals("0")) {
                        continue;
                    }

                    sd.setHas_child(mCursor.getString(8));
                    sd.setMaxSeatsAvailable(mCursor.getString(9));

                    sd.setSpeakerListAsString(mCursor.getString(10));
                    sd.setResourceListAsString(mCursor.getString(11));
                    sd.setSessionFav(mCursor.getString(12) != null && mCursor.getString(12).equals("1"));
                    sd.setCalenderAddId(mCursor.getString(13));

                    sd.setColor(mCursor.getString(14) != null ? mCursor.getString(14) : "#");

                    sd.setSpeakerNameAsString(SpeakerDAO.getInstance(mContext).getSpeakerNames(mCursor.getString(10), db, isFirstName));


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


        return mScheduleDataList;

    }

    /**
     * This method provides liked session on a particula date
     *
     * @param date
     */
    public List<ScheduleData> getLikedSession(String date) {

        boolean isFirstName = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER).equals("firstname_lastname");

        List<ScheduleData> mList = new ArrayList<>();

        String query;
        query = "select sessions.*,likes.isFav,likes.calendarId, session_tracks.color from sessions left join likes on sessions.id=likes.id left join session_tracks on sessions.track = session_tracks.track";// where sessions.start_time like " + date + "%'";
        if (date == null) {
            query += " where sessions.start_time like (select min(date(sessions.start_time)) || '%' from sessions)";
        } else {
            query += " where sessions.start_time like '" + date + "%'";
        }

        query += " and likes.isFav='1';";

        openDB(1);
        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.getCount() > 0) {

                mCursor.moveToFirst();

                ScheduleData mScheduleData;
                do {
                    mScheduleData = new ScheduleData();

                    mScheduleData.setSession_id(mCursor.getString(0));
                    mScheduleData.setName(mCursor.getString(1));
                    mScheduleData.setStart_time(mCursor.getString(2));
                    mScheduleData.setEnd_time(mCursor.getString(3));
                    mScheduleData.setTrack(mCursor.getString(4));
                    mScheduleData.setSummary(mCursor.getString(5));
                    mScheduleData.setLocation(mCursor.getString(6));

                    mScheduleData.setParent_session_id(mCursor.getString(7));
                    if (!mScheduleData.getParent_session_id().equals("0")) {
                        continue;
                    }

                    mScheduleData.setHas_child(mCursor.getString(8));
                    mScheduleData.setMaxSeatsAvailable(mCursor.getString(9));

                    mScheduleData.setSpeakerListAsString(mCursor.getString(10));
                    mScheduleData.setResourceListAsString(mCursor.getString(11));
                    mScheduleData.setSessionFav(mCursor.getString(12) != null && mCursor.getString(12).equals("1"));
                    mScheduleData.setCalenderAddId(mCursor.getString(13));

                    mScheduleData.setColor(mCursor.getString(14) != null ? mCursor.getString(14) : "#");

                    mScheduleData.setSpeakerNameAsString(SpeakerDAO.getInstance(mContext).getSpeakerNames(mCursor.getString(10), db, isFirstName));

                    mList.add(mScheduleData);

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
}
