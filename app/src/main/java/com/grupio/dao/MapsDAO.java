package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.MapsData;
import com.grupio.db.MapsTable;
import com.grupio.helper.MapDataProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 1/9/16.
 */
public class MapsDAO extends BaseDAO {

    /**
     * private constructor
     */
    private MapsDAO(Context mContext) {
        super(mContext);
    }

    public static MapsDAO getInstance(Context mContext) {
        return new MapsDAO(mContext);
    }


    public void insertData(String response) {

        deleteData();

        List<MapsData> mDatalist = new ArrayList<>();
        mDatalist.addAll(MapDataProcessor.getSponsorListFromJSON(mContext, response));

        openDB(1);

        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + MapsTable.MAPS_TABLE
                    + " ( "
                    + MapsTable.MAP_ID + ","
                    + MapsTable.MAP_NAME + ","
                    + MapsTable.MAP_URL + ","
                    + MapsTable.INTERACTIVE + ","
                    + MapsTable.MAP_ORDER +
                    ") VALUES(?,?,?,?,?)"
            );

            if (mDatalist != null && mDatalist.size() > 0) {

                for (int i = 0; i < mDatalist.size(); i++) {
                    stmt.bindString(1, mDatalist.get(i).getMapId());
                    stmt.bindString(2, mDatalist.get(i).getName());
                    stmt.bindString(3, mDatalist.get(i).getUrl());
                    stmt.bindString(4, mDatalist.get(i).getInteractive());
                    stmt.bindString(5, mDatalist.get(i).getOrder());

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

    public List<MapsData> getMapList() {

        List<MapsData> mMapsList = new ArrayList<>();
        openDB(0);
        String query = "select * from " + MapsTable.MAPS_TABLE + " order by " + MapsTable.MAP_ORDER;
        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {
                mCursor.moveToNext();
                MapsData mData;
                do {
                    mData = new MapsData();
                    mData.setMapId(mCursor.getString(0));
                    mData.setName(mCursor.getString(1));
                    mData.setUrl(mCursor.getString(2));
                    mData.setInteractive(mCursor.getString(3));

                    mMapsList.add(mData);
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

        return mMapsList;
    }

    public void deleteData() {
        openDB(1);
        try {
            db.delete(MapsTable.MAPS_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

}
