package com.grupio.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;


import com.grupio.data.MapsData;
import com.grupio.db.MyDbHandler;
import com.grupio.helper.MapDataProcessor;
import com.grupio.dao.BaseDAO;

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
        mDatalist.addAll(MapDataProcessor.getSponsorListFromJSON(response));

        openDB(1);

        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + MyDbHandler.MAPS_TABLE
                            + " ( "
                            + MyDbHandler.MAP_ID + ","
                            + MyDbHandler.MAP_NAME + ","
                            + MyDbHandler.MAP_URL + ","
                            + MyDbHandler.INTERACTIVE + ","
                            + MyDbHandler.MAP_ORDER +
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

    public void deleteData() {
        openDB(1);
        try {
            db.delete(MyDbHandler.MAPS_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

}
