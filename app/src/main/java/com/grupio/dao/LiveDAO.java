package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.LiveData;
import com.grupio.db.LiveTable;
import com.grupio.helper.LiveHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 3/11/16.
 */

public class LiveDAO extends BaseDAO {

    protected LiveDAO(Context mContext) {
        super(mContext);
    }

    public static LiveDAO getInstance(Context mContext) {
        return new LiveDAO(mContext);
    }

    public void insertData(String response) {

        deleteData();

        List<LiveData> mLiveFeedList = new ArrayList<>();

        LiveHelper liveHelper = new LiveHelper();
        mLiveFeedList.addAll(liveHelper.parseList(mContext, response));

        openDB(1);

        SQLiteStatement stmt;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + LiveTable.LIVETABLE + "(" +
                    LiveTable.ID + ","
                    + LiveTable.NAME + ","
                    + LiveTable.URL + ","
                    + LiveTable.IMAGE_URL
                    + ") values (?,?,?,?)");

            if (mLiveFeedList != null && mLiveFeedList.size() > 0) {

                for (int i = 0; i < mLiveFeedList.size(); i++) {
                    stmt.bindString(1, mLiveFeedList.get(i).getId());
                    stmt.bindString(2, mLiveFeedList.get(i).getName());
                    stmt.bindString(3, mLiveFeedList.get(i).getUrl());
                    stmt.bindString(4, mLiveFeedList.get(i).getImageUrl());

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

    public List<LiveData> getData() {

        List<LiveData> mLivefeed = new ArrayList<>();

        openDB(0);

        String query = "SELECT * FROM " + LiveTable.LIVETABLE;

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {

                mCursor.moveToFirst();

                LiveData mLiveData;
                do {
                    mLiveData = new LiveData();
                    mLiveData.setId(mCursor.getString(0));
                    mLiveData.setName(mCursor.getString(1));
                    mLiveData.setUrl(mCursor.getString(2));
                    mLiveData.setImageUrl(mCursor.getString(3));

                    mLivefeed.add(mLiveData);
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
        return mLivefeed;
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
