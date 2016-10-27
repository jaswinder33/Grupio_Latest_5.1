package com.grupio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.grupio.data.VersionData;
import com.grupio.db.VersionTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 22/8/16.
 */
public class VersionDao extends BaseDAO {

    public static final String CUSTOM_MENU_VERSION = "custom_menu_version";
    public static final String MENU_VERSION = "menu_version";
    public static final String GRAPHICS_VERSION = "graphics_version";
    public static final String TRACK_VERSION = "track_version";
    public static final String LANGUAGE_VERSION = "language_version";
    public static final String ADS_VERSION = "ads_version";
    public static final String SURVEYS_VERSION = "surveys_version";
    public static final String LOGISTICS_VERSION = "logistics_version";
    public static final String MAP_VERSION = "map_version";
    public static final String NEWSFEEDMENU_VERSION = "newsfeedmenu_version";
    public static final String MATCHES_VERSION = "matches_version";
    public static final String LIVEFEED_VERSION = "livefeed_version";
    public static final String SPONSORS_VERSION = "sponsors_version";
    public static final String EXHIBITORS_VERSION = "exhibitors_version";
    public static final String ATTENDEE_VERSION = "attendee_version";
    public static final String SPEAKER_VERSION = "speaker_version";
    public static final String SCHEDULE_VERSION = "schedule_version";
    public static final String EVENT_VERSION = "event_version";
    public static final String APP_VERSION = "app_version";
    public static final String FORCE_UPGRADE = "force_upgrade";
    private static final String TAG = "Version DAO";
    private static VersionDao mVersionDao;


    /**
     * private constructor
     */
    private VersionDao(Context mContext) {
        super(mContext);
    }

    /**
     * Returns instance of class
     *
     * @return
     */
    public static VersionDao getInstance(Context mContext) {
        if (mVersionDao == null) {
            mVersionDao = new VersionDao(mContext);
        }
        return mVersionDao;
    }

    public void insertDataInNewColumn(List<VersionData> value) {

        openDB(1);

        String query;
        Cursor mCursor = null;

        for (int i = 0; i < value.size(); i++) {

            query = "update " + VersionTable.VERSION_TABLE + " set " + VersionTable.NEW_VERSION + "='" + value.get(i).newVersion + "' where " + VersionTable.VERSION_NAME + "='" + value.get(i).name + "';";

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
            }
        }

        closeDb();

    }

    public void insertDataInOldColumn(VersionData vData) {

        Log.i(TAG, "insertDataInOldColumn: \nName: " + vData.name + "\nversion: " + vData.oldVersion);
        openDB(1);

        String query;
        Cursor mCursor = null;

        query = "SELECT EXISTS (SELECT * FROM " + VersionTable.VERSION_TABLE + " WHERE " + VersionTable.VERSION_NAME + "='" + vData.name + "');";

        try {
            mCursor = db.rawQuery(query, null);
            boolean exists = false;
            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    exists = mCursor.getInt(0) == 1;
                } while (mCursor.moveToNext());
            }

            if (exists) {

                query = "update " + VersionTable.VERSION_TABLE + " set " + VersionTable.OLD_VERSION + "=" + vData.oldVersion + " where " + VersionTable.VERSION_NAME + "='" + vData.name + "';";

                mCursor = db.rawQuery(query, null);

                if (mCursor != null && mCursor.moveToFirst())
                    mCursor.moveToFirst();

            } else {
                ContentValues cv = new ContentValues();
                cv.put(VersionTable.VERSION_NAME, vData.name);
                cv.put(VersionTable.OLD_VERSION, vData.oldVersion);
                cv.put(VersionTable.NEW_VERSION, "0");

                db.insert(VersionTable.VERSION_TABLE, null, cv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }

        closeDb();

    }


    public List<VersionData> getMisMatchedVersions() {

        List<VersionData> versionList = new ArrayList<>();
        openDB(0);
        String query = "select * from " + VersionTable.VERSION_TABLE + " where " + VersionTable.NEW_VERSION + " != " + VersionTable.OLD_VERSION + ";";
        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);
            if (mCursor != null && mCursor.moveToFirst()) {
                VersionData vData;

                do {
                    vData = new VersionData();
                    vData.name = mCursor.getString(0);
                    vData.oldVersion = mCursor.getString(1);
                    vData.newVersion = mCursor.getString(2);
                    versionList.add(vData);

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

        return versionList;
    }


}
