package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.LogisticsData;
import com.grupio.db.LogisticsTable;
import com.grupio.helper.LogisticsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 24/10/16.
 */

public class LogisticsDAO extends BaseDAO {


    private LogisticsDAO(Context mContext) {
        super(mContext);
    }

    public static LogisticsDAO getInstance(Context mContext) {
        return new LogisticsDAO(mContext);
    }

    public void insertData(String response) {

        deleteTable();

        List<LogisticsData> mLogisticsList = new ArrayList<>();

        LogisticsHelper mHelper = new LogisticsHelper();
        mLogisticsList.addAll(mHelper.parseData(mContext, response));

        openDB(1);

        try {
            SQLiteStatement stmt = null;
            db.beginTransaction();

            stmt = db.compileStatement("Insert into " + LogisticsTable.LOGISTICS_TABLE + "("
                    + LogisticsTable.ID + ", "
                    + LogisticsTable.NAME + ", "
                    + LogisticsTable.URL + ", "
                    + LogisticsTable.DOCTYPE + ", "
                    + LogisticsTable.TYPE + ", "
                    + LogisticsTable.ELEMENT_ORDER
                    + ") values(?,?,?,?,?,?)");

            if (mLogisticsList != null && !mLogisticsList.isEmpty()) {

                for (int i = 0; i < mLogisticsList.size(); i++) {

                    stmt.bindString(1, mLogisticsList.get(i).getLogistics_id());
                    stmt.bindString(2, mLogisticsList.get(i).getName());
                    stmt.bindString(3, mLogisticsList.get(i).getUrl());
                    stmt.bindString(4, mLogisticsList.get(i).getDoctype());
                    stmt.bindString(5, mLogisticsList.get(i).getType());
                    stmt.bindString(6, mLogisticsList.get(i).getOrder());

                    stmt.executeInsert();
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

    public List<LogisticsData> getLogistics(String type) {

        List<LogisticsData> mLogisticsList = new ArrayList<>();

        openDB(0);

        String query;

        if (type.equals("") || type == null) {
            query = "select * from " + LogisticsTable.LOGISTICS_TABLE + "order by " + LogisticsTable.ELEMENT_ORDER + ";";
        } else {
            query = "select * from " + LogisticsTable.LOGISTICS_TABLE + " where  upper(" + LogisticsTable.TYPE + ")=upper('" + type + "') order by " + LogisticsTable.ELEMENT_ORDER + ";";
        }

        Cursor mcursor = null;

        try {
            mcursor = db.rawQuery(query, null);

            if (mcursor != null) {
                mcursor.moveToFirst();

                LogisticsData mData;
                do {
                    mData = new LogisticsData();
                    mData.setLogistics_id(mcursor.getString(0));
                    mData.setName(mcursor.getString(1));
                    mData.setUrl(mcursor.getString(2));
                    mData.setDoctype(mcursor.getString(3));
                    mData.setType(mcursor.getString(4));
                    mData.setOrder(mcursor.getString(5));

                    mLogisticsList.add(mData);
                } while (mcursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (mcursor != null && !mcursor.isClosed()) {
                mcursor.close();
                closeDb();
            }
        }


        return mLogisticsList;
    }


    public void deleteTable() {
        openDB(0);

        try {
            db.delete(LogisticsTable.LOGISTICS_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }
    }

}
