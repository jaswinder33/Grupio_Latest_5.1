package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.SponsorData;
import com.grupio.db.SponsorTable;
import com.grupio.helper.SponsorHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JSN on 1/9/16.
 */
public class SponsorDAO extends BaseDAO {

    private SponsorDAO(Context mContext) {
        super(mContext);
    }

    public static SponsorDAO getInstance(Context mContext) {
        return new SponsorDAO(mContext);
    }

    public void insertData(String respones) {

        deleteData();

        List<SponsorData> mList = new ArrayList<>();

        SponsorHelper sHelper = new SponsorHelper();
        mList.addAll(sHelper.parseData(respones, mContext));

        if (mList.size() > 0) {
            openDB(1);

            db.beginTransaction();
            db.setTransactionSuccessful();
            try {
                SQLiteStatement stmt = null;

                stmt = db.compileStatement("Insert into "
                        + SponsorTable.SPONSOR_TABLE + "("
                        + SponsorTable.SPONSORID + ", "
                        + SponsorTable.NAME + ", "
                        + SponsorTable.TYPE + ", "
                        + SponsorTable.URL + ", "
                        + SponsorTable.LARGEURL + ", "
                        + SponsorTable.WEBURL + ", "
                        + SponsorTable.SPONSORSESSIONS + ", "
                        + SponsorTable.PHONE + ", "
                        + SponsorTable.EMAIL + ", "
                        + SponsorTable.BOOTH + ", "
                        + SponsorTable.DESCRIPTION + ", "
                        + SponsorTable.SUMMARY + ", "
                        + SponsorTable.SPONSORLINK +
                        ") values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

                for (int i = 0; i < mList.size(); i++) {
                    stmt.bindString(1, mList.get(i).sponsorId);
                    stmt.bindString(2, mList.get(i).name);
                    stmt.bindString(3, mList.get(i).type);
                    stmt.bindString(4, mList.get(i).url);
                    stmt.bindString(5, mList.get(i).largeUrl);
                    stmt.bindString(6, mList.get(i).webUrl);
                    stmt.bindString(7, mList.get(i).sponsorSessions);
                    stmt.bindString(8, mList.get(i).phone);
                    stmt.bindString(9, mList.get(i).email);
                    stmt.bindString(10, mList.get(i).booth);
                    stmt.bindString(11, mList.get(i).description);
                    stmt.bindString(12, mList.get(i).summary);
                    stmt.bindString(13, mList.get(i).sponsorLink);

                    stmt.executeInsert();
                    stmt.clearBindings();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            db.endTransaction();
            closeDb();
        }


    }

    public List<SponsorData> getSponsorList(String queryStr) {

        List<SponsorData> mSponsorList = new ArrayList<>();

        String query = "select * from " + SponsorTable.SPONSOR_TABLE;

        if (queryStr != null) {
            query += " where " + SponsorTable.NAME + " like '" + queryStr + "%';";
        }
        openDB(0);

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null) {
                mCursor.moveToFirst();

                SponsorData sData;
                do {
                    sData = new SponsorData();

                    sData.sponsorId = mCursor.getString(0);
                    sData.name = mCursor.getString(1);
                    sData.type = mCursor.getString(2);
                    sData.url = mCursor.getString(3);
                    sData.largeUrl = mCursor.getString(4);
                    sData.webUrl = mCursor.getString(5);
                    sData.sponsorSessions = mCursor.getString(6);
                    sData.phone = mCursor.getString(7);
                    sData.email = mCursor.getString(8);
                    sData.booth = mCursor.getString(9);
                    sData.description = mCursor.getString(10);
                    sData.summary = mCursor.getString(11);
                    sData.sponsorLink = mCursor.getString(12);

                    mSponsorList.add(sData);
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

        return mSponsorList;
    }

    public void getType() {
        String query = "";
    }

    public void deleteData() {

        openDB(1);
        try {
            db.delete(SponsorTable.SPONSOR_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDb();

    }

}
