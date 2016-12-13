package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.AdsData;
import com.grupio.db.AdsTable;
import com.grupio.helper.AdsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 12/12/16.
 */

public class AdsDAO extends BaseDAO {

    protected AdsDAO(Context mContext) {
        super(mContext);
    }

    public static AdsDAO getInstance(Context mContext) {
        return new AdsDAO(mContext);
    }

    public void insert(Context context, String response) {

        List<AdsData> mAdList = new ArrayList<>();

        AdsHelper adsHelper = new AdsHelper();
        mAdList.addAll(adsHelper.parseData(response, context));

        if (!mAdList.isEmpty()) {

            openDB(1);

            db.beginTransaction();

            SQLiteStatement stmt = db.compileStatement(AdsTable.INSERT_DATA);

            try {
                for (int i = 0; i < mAdList.size(); i++) {
                    stmt.bindString(1, mAdList.get(i).id);
                    stmt.bindString(2, mAdList.get(i).adHtml);
                    stmt.bindString(3, mAdList.get(i).imageUrl);
                    stmt.bindString(4, mAdList.get(i).goToUrl);
                    stmt.bindString(5, mAdList.get(i).adsOrder);
                    stmt.bindString(6, mAdList.get(i).section);
                    stmt.bindString(7, mAdList.get(i).imageUrl480);
                    stmt.bindString(8, mAdList.get(i).imageUrl800);
                    stmt.bindString(9, mAdList.get(i).status);

                    stmt.executeInsert();
                    stmt.clearBindings();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.setTransactionSuccessful();
                db.endTransaction();
                closeDb();
            }
        }
    }

    public List<AdsData> showBanner(String screenName) {

        List<AdsData> mList = new ArrayList<>();
        AdsData adsData;

        openDB(0);
        String query = "select " + AdsTable.SECTION + " from " + AdsTable.ADS_TABLE + " where " + AdsTable.SECTION + " like '" + screenName + ",%' or  " + AdsTable.SECTION + " like '%," + screenName + ",%'  or  " + AdsTable.SECTION + " like '%," + screenName + "';";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    adsData = new AdsData();
                    adsData.id = cursor.getString(0);
                    adsData.adHtml = cursor.getString(1);
                    adsData.imageUrl = cursor.getString(2);
                    adsData.goToUrl = cursor.getString(3);
                    adsData.adsOrder = cursor.getString(4);
                    adsData.section = cursor.getString(5);
                    adsData.imageUrl480 = cursor.getString(6);
                    adsData.imageUrl800 = cursor.getString(7);
                    adsData.status = cursor.getString(8);
                    mList.add(adsData);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            closeDb();
        }

        return mList;
    }

    public void getAdsList() {

    }
}
