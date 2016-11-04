package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.SurveyData;
import com.grupio.db.LiveTable;
import com.grupio.db.SurveyTable;
import com.grupio.helper.SurveyHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 3/11/16.
 */

public class SurveyDAO extends BaseDAO {

    protected SurveyDAO(Context mContext) {
        super(mContext);
    }

    public static SurveyDAO getInstance(Context mContext) {
        return new SurveyDAO(mContext);
    }

    public void insertData(String response) {

        deleteData();

        List<SurveyData> mSurveyList = new ArrayList<>();

        SurveyHelper mSurveyHelper = new SurveyHelper();
        mSurveyList.addAll(mSurveyHelper.parseList(mContext, response));

        openDB(1);

        SQLiteStatement stmt;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + SurveyTable.SURVEY_TABLE + "(" +
                    SurveyTable.NAME + ","
                    + SurveyTable.URL
                    + ") values (?,?)");

            if (mSurveyList != null && mSurveyList.size() > 0) {

                for (int i = 0; i < mSurveyList.size(); i++) {
                    stmt.bindString(1, mSurveyList.get(i).getName());
                    stmt.bindString(2, mSurveyList.get(i).getUrl());

                    stmt.executeInsert();
                    stmt.clearBindings();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        closeDb();

    }

    public List<SurveyData> getData() {

        List<SurveyData> mSurveyList = new ArrayList<>();

        openDB(0);

        String query = "SELECT * FROM " + SurveyTable.SURVEY_TABLE;

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {

                mCursor.moveToFirst();

                SurveyData mSurveyData;
                do {
                    mSurveyData = new SurveyData();
                    mSurveyData.setName(mCursor.getString(0));
                    mSurveyData.setUrl(mCursor.getString(1));

                    mSurveyList.add(mSurveyData);
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
        return mSurveyList;
    }

    public void deleteData() {

        openDB(1);

        try {
            db.delete(LiveTable.LIVETABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDb();
        }


    }

}
