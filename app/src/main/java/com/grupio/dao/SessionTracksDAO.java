package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.grupio.data.TrackData;
import com.grupio.db.SessionTracksTable;
import com.grupio.helper.ScheduleHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 24/8/16.
 */
public class SessionTracksDAO extends BaseDAO {

    /**
     * private constructor
     */
    private SessionTracksDAO(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }


    public static SessionTracksDAO getInstance(Context mContext) {
        return new SessionTracksDAO(mContext);
    }

    public void insertData(String response) {

        Log.i("DB start", System.currentTimeMillis() + "");
        deleteData();

        List<TrackData> mDatalist = new ArrayList<>();

        ScheduleHelper sHelper = new ScheduleHelper();
        mDatalist.addAll(sHelper.parseTrackList(response));

        openDB(1);

        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + SessionTracksTable.SESSION_TRACKS_TABLE
                    + " ( "
                    + SessionTracksTable.ID + ","
                    + SessionTracksTable.TRACK + ","
                    + SessionTracksTable.COLOR + ","
                    + SessionTracksTable.CATEGORY + ","
                    + SessionTracksTable.TRACK_ORDER +
                    ") VALUES(?,?,?,?,?)"
            );

            if (mDatalist != null && mDatalist.size() > 0) {

                for (int i = 0; i < mDatalist.size(); i++) {
                    stmt.bindString(1, mDatalist.get(i).id);
                    stmt.bindString(2, mDatalist.get(i).track);
                    stmt.bindString(3, mDatalist.get(i).color);
                    stmt.bindString(4, mDatalist.get(i).category);
                    stmt.bindString(5, mDatalist.get(i).order);

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

        Log.i("DB end", System.currentTimeMillis() + "");
    }

    public List<TrackData> getTrackList() {

        List<TrackData> mTrackList = new ArrayList<>();

        openDB(0);

//        String query =
//                "select * from "+MyDbHandler.SESSION_TRACKS_TABLE+" " +
//                        "order by " +
//                        "case " +
//                        "when " + MyDbHandler.TRACK_ORDER + " <> '0' " +
//                        "then " + MyDbHandler.TRACK_ORDER + "  " +
//                        "else " + MyDbHandler.TRACK + "  collate nocase end;";


        String query = "select * from " + SessionTracksTable.SESSION_TRACKS_TABLE + " order by " +
                "case " +
                "when (" + SessionTracksTable.TRACK_ORDER + " is not null || " + SessionTracksTable.TRACK_ORDER + " <> 0 ) " +
                "then cast(" + SessionTracksTable.TRACK_ORDER + " as int)  " +
                "else " + SessionTracksTable.TRACK + " collate nocase end;";

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.moveToFirst()) {

                TrackData td;

                do {
                    td = new TrackData();

                    td.id = mCursor.getString(0);
                    td.track = mCursor.getString(1);
                    td.color = mCursor.getString(2);
                    td.category = mCursor.getString(3);
                    td.order = mCursor.getString(4);

                    mTrackList.add(td);

                } while (mCursor.moveToNext());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
                closeDb();
            }
        }

        return mTrackList;
    }

    public List<String> getTrackAsStringList() {

        String query = "select " + SessionTracksTable.TRACK + " from " + SessionTracksTable.SESSION_TRACKS_TABLE + " order by " + SessionTracksTable.TRACK + " collate nocase;";

        List<String> mList = new ArrayList<>();

        openDB(0);

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    mList.add(mCursor.getString(0));
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

    public boolean checkIfTrackExist() {

        boolean isTrackPresent = false;

        String query = "select exists(select " + SessionTracksTable.TRACK + " from " + SessionTracksTable.SESSION_TRACKS_TABLE + ");";

        openDB(0);

        Cursor mCursor = null;


        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    isTrackPresent = mCursor.getString(0).equals("1");

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

        return isTrackPresent;

    }

    public void deleteData() {
        openDB(0);
        try {
            db.delete(SessionTracksTable.SESSION_TRACKS_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

    public String getTrackName(String trackid) {

        openDB(0);

        String trackname = "";

        String query = "select " + SessionTracksTable.TRACK + " from session_tracks where id=" + trackid + ";";


        Cursor mCursor = null;
        try {

            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {
                mCursor.moveToFirst();

                do {
                    trackname = mCursor.getString(0);
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

        return trackname;

    }


}
