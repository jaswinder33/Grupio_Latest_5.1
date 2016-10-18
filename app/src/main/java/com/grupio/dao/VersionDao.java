package com.grupio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.grupio.data.VersionData;
import com.grupio.db.MyDbHandler;

import java.util.List;

/**
 * Created by JSN on 22/8/16.
 */
public class VersionDao extends BaseDAO{

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

            query = "update " + MyDbHandler.VERSION_TABLE + " set " + MyDbHandler.NEW_VERSION + "='" + value.get(i).newVersion  + "' where "+ MyDbHandler.VERSION_NAME + "='" + value.get(i).name+"';";

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
//        openDB(1);
//
//        String query;
//        Cursor mCursor = null;
//
//        query = "SELECT EXISTS (SELECT * FROM "+MyDbHandler.VERSION_TABLE+" WHERE "+MyDbHandler.VERSION_NAME+"='"+vData.name+"');";
//
//        try {
//            mCursor = db.rawQuery(query, null);
//            boolean exists = false;
//            if (mCursor != null && mCursor.moveToFirst()) {
//                do{
//                    exists = mCursor.getInt(0) == 1 ? true : false;
//                }while (mCursor.moveToNext());
//            }
//
//            if(exists){
//
//                query = "update " + MyDbHandler.VERSION_TABLE + " set " + MyDbHandler.OLD_VERSION + "=" + vData.oldVersion + " where " + MyDbHandler.VERSION_NAME + "='" + vData.name + "';";
//
//                mCursor = db.rawQuery(query, null);
//
//                if(mCursor != null && mCursor.moveToFirst())
//                mCursor.moveToFirst();
//
//            }else{
//                ContentValues cv =new ContentValues();
//                cv.put(MyDbHandler.VERSION_NAME,vData.name);
//                cv.put(MyDbHandler.OLD_VERSION,vData.oldVersion);
//                cv.put(MyDbHandler.NEW_VERSION,"0");
//
//                db.insert(MyDbHandler.VERSION_TABLE, null, cv);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (mCursor != null) {
//                mCursor.close();
//            }
//        }
//
//        closeDb();

    }


    public void updateNewVersion() {

    }

    public void updateOlderVersion() {

    }


    public void deleteNewData() {

    }


}
