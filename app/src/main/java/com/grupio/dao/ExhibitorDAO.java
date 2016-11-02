package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.grupio.data.ExhibitorData;
import com.grupio.db.ExhibitorLikeTable;
import com.grupio.db.ExhibitorTable;
import com.grupio.helper.ExhibitorProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 26/7/16.
 */
public class ExhibitorDAO extends BaseDAO {

    //ExhibitorDAO object
    private static ExhibitorDAO mExhibitorDao;

    /**
     * private constructor
     */
    private ExhibitorDAO(Context mContext) {
        super(mContext);
    }


    /**
     * Returns instance of class
     *
     * @return
     */
    public static ExhibitorDAO getInstance(Context mContext) {
        if (mExhibitorDao == null) {
            mExhibitorDao = new ExhibitorDAO(mContext);
        }
        return mExhibitorDao;
    }

    /**
     * insert exhibitor data directly from API in db
     */
    public void insert(String responseFromAPI) {


        deleteExhibitors();

        List<ExhibitorData> mDatalist = new ArrayList<>();

        ExhibitorProcessor ep = new ExhibitorProcessor();
        mDatalist.addAll(ep.getExhibitorsListFromJSON(mContext, responseFromAPI));

        openDB(1);

        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + ExhibitorTable.EXHIBITOR_TABLE
                    + " ( "
                    + ExhibitorTable.ID + ","
                    + ExhibitorTable.NAME + ","
                    + ExhibitorTable.IMAGE + ","
                    + ExhibitorTable.LARGEIMAGE + ","
                    + ExhibitorTable.BIO + ","
                    + ExhibitorTable.WEBSITE + ","
                    + ExhibitorTable.CATEGORY + ","
                    + ExhibitorTable.SUB_CATEGORY + ","
                    + ExhibitorTable.EMAIL + ","
                    + ExhibitorTable.PRIMARYPHONE + ","
                    + ExhibitorTable.ADDRESS + ","
                    + ExhibitorTable.isFav + ","
                    + ExhibitorTable.LOCATION + ","
                    + ExhibitorTable.SESSIONLIST + ","
                    + ExhibitorTable.RESOURCELINKS + ","
                    + ExhibitorTable.ATTENDEELIST + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

            );

            if (mDatalist != null && mDatalist.size() > 0) {

                for (int i = 0; i < mDatalist.size(); i++) {
                    stmt.bindString(1, mDatalist.get(i).getExhibitorId());
                    stmt.bindString(2, mDatalist.get(i).getName());
                    stmt.bindString(3, mDatalist.get(i).getImage());
                    stmt.bindString(4, mDatalist.get(i).getImageLarge());
                    stmt.bindString(5, mDatalist.get(i).getDescription());
                    stmt.bindString(6, mDatalist.get(i).getWeburl());
                    stmt.bindString(7, mDatalist.get(i).getCategory());
                    stmt.bindString(8, "");
                    stmt.bindString(9, mDatalist.get(i).getContact_email());
                    stmt.bindString(10, mDatalist.get(i).getContact_phone());
                    stmt.bindString(11, mDatalist.get(i).getAddress());
                    stmt.bindString(12, "0");
                    stmt.bindString(13, mDatalist.get(i).getLocation());
                    stmt.bindString(14, mDatalist.get(i).getSessionListAsString());
                    stmt.bindString(15, mDatalist.get(i).getResourceListAsSrings());
                    stmt.bindString(16, mDatalist.get(i).getAttendeeListAsString());

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


    /**
     * Return list of category of all exhibitors
     * <p/>
     * Sample query
     * select distinct category from exhibitors where category !="" order by category COLLATE NOCASE;
     */
    public List<String> getCategoryList() {

        List<String> mList = new ArrayList<>();


        String query = "select distinct " + ExhibitorTable.CATEGORY + " from " + ExhibitorTable.EXHIBITOR_TABLE + " where " + ExhibitorTable.CATEGORY + " !=\"\" order by " + ExhibitorTable.CATEGORY + " COLLATE NOCASE;";

        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

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

    /**
     * return list of exhibitors from database.
     *
     * @param i used as offset for pagination not used till now
     *          <p/>
     *          Sample Query:
     *          select exhibitors.*, likes.isFav  from exhibitors left join likes on exhibitors.id=likes.id  order by name COLLATE NOCASE;
     */
    public List<ExhibitorData> getExhibitorList(int i) {

        List<ExhibitorData> mList = new ArrayList<>();

        String query;

//removed limit
//            if (isFirstName) {
//                query = "select * from " + ExhibitorTable.ATTENDEE_TABLE + " order by " + MyDbHandler.FIRSTNAME + " COLLATE NOCASE limit 30 offset " + i * 30 + ";";
//            } else {
//                query = "select * from " + MyDbHandler.ATTENDEE_TABLE + " order by " + MyDbHandler.LASTNAME + " COLLATE NOCASE limit 30 offset " + i * 30 + ";";
//            }

//        query = "select * from " + MyDbHandler.EXHIBITOR_TABLE + " order by " + MyDbHandler.NAME + " COLLATE NOCASE;";

        query = "select exhibitors.*, likes.isFav  from " + ExhibitorTable.EXHIBITOR_TABLE + " left join " + ExhibitorLikeTable.LIKE_TABLE + " on exhibitors.id=likes.id  order by name COLLATE NOCASE;";

        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                ExhibitorData aData;
                do {
                    aData = new ExhibitorData();

                    aData.setExhibitorId(mCursor.getString(0));
                    aData.setName(mCursor.getString(1));
                    aData.setImage(mCursor.getString(2));
                    aData.setImageLarge(mCursor.getString(3));
                    aData.setDescription(mCursor.getString(4));
                    aData.setWeburl(mCursor.getString(5));
                    aData.setCategory(mCursor.getString(6));
                    aData.setContact_email(mCursor.getString(8));
                    aData.setContact_phone(mCursor.getString(9));
                    aData.setAddress(mCursor.getString(10));
//                    aData.setIsFav(mCursor.getString(11));
                    aData.setLocation(mCursor.getString(12));
                    aData.setAttendeeListAsString(mCursor.getString(13));
                    aData.setSessionListAsString(mCursor.getString(14));
                    aData.setResourceListAsSrings(mCursor.getString(15));
                    aData.setIsFav(mCursor.getString(16));

                    mList.add(aData);

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


    /**
     * Return list of exhibitors according to particular category
     *
     * @param category provides category to which all exhibitors will belong.
     * @param i        used as offset for pagination
     *                 <p/>
     *                 Sample query
     *                 select * from exhibitors where category='server' order by firstname COLLATE NOCASE limit 30 offset 30;
     * @return list of Exhibitors belongs to particular category
     */
    public List<ExhibitorData> sortExhibitorByCategory(String category, int i) {

        List<ExhibitorData> mList = new ArrayList<>();

        String query;

        query = "select exhibitors.*, likes.isFav  from exhibitors left join likes on exhibitors.id=likes.id   where category like  '" + category + "' or category like  '%," + category + "' or category like  '" + category + ",%'  or category like '%," + category + ",%' order by name COLLATE NOCASE;";

        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                ExhibitorData aData;
                do {
                    aData = new ExhibitorData();

                    aData.setExhibitorId(mCursor.getString(0));
                    aData.setName(mCursor.getString(1));
                    aData.setImage(mCursor.getString(2));
                    aData.setImageLarge(mCursor.getString(3));
                    aData.setDescription(mCursor.getString(4));
                    aData.setWeburl(mCursor.getString(5));
                    aData.setCategory(mCursor.getString(6));
                    aData.setContact_email(mCursor.getString(8));
                    aData.setContact_phone(mCursor.getString(9));
                    aData.setAddress(mCursor.getString(10));
//                    aData.setIsFav(mCursor.getString(11));
                    aData.setLocation(mCursor.getString(12));
                    aData.setAttendeeListAsString(mCursor.getString(13));
                    aData.setSessionListAsString(mCursor.getString(14));
                    aData.setResourceListAsSrings(mCursor.getString(15));
                    aData.setIsFav(mCursor.getString(16));

                    mList.add(aData);
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

    /**
     * Fetch detail of exhibitor of particular id
     *
     * @param id id of exhibitor whose detail we've to fetch from db.
     * @return object of exhibitor
     */
    public ExhibitorData getExhibitorDetal(String id) {

        openDB(0);

        ExhibitorData aData = null;

        String query = "select exhibitors.*, likes.isFav  from " + ExhibitorTable.EXHIBITOR_TABLE + " left join " + ExhibitorLikeTable.LIKE_TABLE + " on exhibitors.id=likes.id  where  exhibitors.id='" + id + "' order by name COLLATE NOCASE;";


        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                do {
                    aData = new ExhibitorData();
                    aData.setExhibitorId(mCursor.getString(0));
                    aData.setName(mCursor.getString(1));
                    aData.setImage(mCursor.getString(2));
                    aData.setImageLarge(mCursor.getString(3));
                    aData.setDescription(mCursor.getString(4));
                    aData.setWeburl(mCursor.getString(5));
                    aData.setCategory(mCursor.getString(6));
                    aData.setContact_email(mCursor.getString(8));
                    aData.setContact_phone(mCursor.getString(9));
                    aData.setAddress(mCursor.getString(10));
//                    aData.setIsFav(mCursor.getString(11));
                    aData.setLocation(mCursor.getString(12));
                    aData.setAttendeeListAsString(mCursor.getString(13));
                    aData.setSessionListAsString(mCursor.getString(14));
                    aData.setResourceListAsSrings(mCursor.getString(15));
                    aData.setIsFav(mCursor.getString(16));

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


        return aData;
    }


    /**
     * Return list of exhibitors as user search exhibitors by name
     *
     * @param category name of category to which exhbitor belong
     * @param queryStr search query by user
     *                 <p/>
     *                 Sample query
     *                 select * from exhibitors where  category='server' (firstName like 'ma%' or lastName like '') order by firstname COLLATE NOCASE;
     * @return list of all exhibitors which are returned by query
     */
    public List<ExhibitorData> searchExhibitorByName(String category, String queryStr) {

        List<ExhibitorData> mList = new ArrayList<>();

        String query;

        if (category == null) {
            query = "select exhibitors.*, likes.isFav  from " + ExhibitorTable.EXHIBITOR_TABLE + " left join " + ExhibitorLikeTable.LIKE_TABLE + " on exhibitors.id=likes.id  where " + ExhibitorTable.NAME + " like '" + queryStr + "%'";
        } else {
            query = "select exhibitors.*, likes.isFav  from " + ExhibitorTable.EXHIBITOR_TABLE + " left join " + ExhibitorLikeTable.LIKE_TABLE + " on exhibitors.id=likes.id  where " + ExhibitorTable.NAME + " like '" + queryStr + "%'";

            query += "and  (category like  '" + category + "' or category like  '%," + category + "' or category like  '" + category + ",%'  or category like '%," + category + ",%') ";
        }

        query += "order by " + ExhibitorTable.NAME + " COLLATE NOCASE;";

        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                ExhibitorData aData;
                do {
                    aData = new ExhibitorData();
                    aData.setExhibitorId(mCursor.getString(0));
                    aData.setName(mCursor.getString(1));
                    aData.setImage(mCursor.getString(2));
                    aData.setImageLarge(mCursor.getString(3));
                    aData.setDescription(mCursor.getString(4));
                    aData.setWeburl(mCursor.getString(5));
                    aData.setCategory(mCursor.getString(6));
                    aData.setContact_email(mCursor.getString(8));
                    aData.setContact_phone(mCursor.getString(9));
                    aData.setAddress(mCursor.getString(10));
//                    aData.setIsFav(mCursor.getString(11));
                    aData.setLocation(mCursor.getString(12));
                    aData.setAttendeeListAsString(mCursor.getString(13));
                    aData.setSessionListAsString(mCursor.getString(14));
                    aData.setResourceListAsSrings(mCursor.getString(15));
                    aData.setIsFav(mCursor.getString(16));

                    mList.add(aData);

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


    public void likeUnlikeExhb(String exhbId, int operation) {
        openDB(1);

        SQLiteStatement stmt;

        if (operation == 1) {

            stmt = db.compileStatement("INSERT INTO " + ExhibitorLikeTable.LIKE_TABLE
                    + " ( "
                    + ExhibitorLikeTable.ID + ","
                    + ExhibitorLikeTable.isFav + ") VALUES(?,?)");

            stmt.bindString(1, exhbId);
            stmt.bindString(2, "1");
            stmt.execute();

        } else {
            try {
                db.delete(ExhibitorLikeTable.LIKE_TABLE, ExhibitorLikeTable.ID + "=?", new String[]{exhbId});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        closeDb();
    }


    public void deleteExhibitors() {

        openDB(1);
        try {
            db.delete(ExhibitorTable.EXHIBITOR_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

}
