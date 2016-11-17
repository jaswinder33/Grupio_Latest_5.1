package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.grupio.data.SpeakerData;
import com.grupio.db.SpeakerTable;
import com.grupio.helper.SpeakerProcessor;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 26/7/16.
 */
public class SpeakerDAO extends BaseDAO {

    private static SpeakerDAO mSpeakerDAO;

    /**
     * private constructor
     */
    private SpeakerDAO(Context mContext) {
        super(mContext);
    }


    /**
     * Returns instance of class
     *
     * @return
     */
    public static SpeakerDAO getInstance(Context mContext) {
        if (mSpeakerDAO == null) {
            mSpeakerDAO = new SpeakerDAO(mContext);
        }
        return mSpeakerDAO;
    }

    /**
     * insert attendee data directly from API in db
     */
    public void insert(String responseFromAPI) {


        deleteSpeakers();

        Log.i("DB start", System.currentTimeMillis() + "");

        List<SpeakerData> mDatalist = new ArrayList<>();

        SpeakerProcessor sp = new SpeakerProcessor();
        mDatalist.addAll(sp.getSpeakersListFromJSON(mContext, responseFromAPI));

        openDB(1);


        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + SpeakerTable.SPEAKER_TABLE
                    + " ( "
                    + SpeakerTable.ID + ","
                    + SpeakerTable.FIRSTNAME + ","
                    + SpeakerTable.LASTNAME + ","

                    + SpeakerTable.COMPANY + ","
                    + SpeakerTable.TITLE + ","
                    + SpeakerTable.EMAIL + ","

                    + SpeakerTable.TYPE + ","
                    + SpeakerTable.PRIMARYPHONE + ","
                    + SpeakerTable.SECONDARYPHONE + ","

                    + SpeakerTable.IMAGE + ","
                    + SpeakerTable.LARGEIMAGE + ","

                    + SpeakerTable.BIO + ","
                    + SpeakerTable.LINKEDINID + ","
                    + SpeakerTable.TWITTERID + ","
                    + SpeakerTable.WEBSITE + ","

                    + SpeakerTable.CATEGORY + ","
                    + SpeakerTable.SESSIONLIST + ","
                    + SpeakerTable.RESOURCELINKS + ","

                    + SpeakerTable.ENABLECONTACTS + ","
                    + SpeakerTable.HIDECONTACTINFO + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

            );

            if (mDatalist != null && mDatalist.size() > 0) {

                for (int i = 0; i < mDatalist.size(); i++) {
                    stmt.bindString(1, mDatalist.get(i).getAttendee_id());
                    stmt.bindString(2, mDatalist.get(i).getFirst_name());
                    stmt.bindString(3, mDatalist.get(i).getLast_name());

                    stmt.bindString(4, mDatalist.get(i).getCompany());
                    stmt.bindString(5, mDatalist.get(i).getTitle());
                    stmt.bindString(6, mDatalist.get(i).getEmail());

                    stmt.bindString(7, mDatalist.get(i).getType());
                    stmt.bindString(8, mDatalist.get(i).getPrimary_phone());
                    stmt.bindString(9, mDatalist.get(i).getSecondary_phone());

                    stmt.bindString(10, mDatalist.get(i).getImageURL());
                    stmt.bindString(11, mDatalist.get(i).getLargeImageUrl());

                    stmt.bindString(12, mDatalist.get(i).getBio());
                    stmt.bindString(13, mDatalist.get(i).getLinkedin());
                    stmt.bindString(14, mDatalist.get(i).getTwitter_id());
                    stmt.bindString(15, mDatalist.get(i).getWebsite());

                    stmt.bindString(16, mDatalist.get(i).getCategory());

                    stmt.bindString(17, mDatalist.get(i).getSessionListAsString());
                    stmt.bindString(18, mDatalist.get(i).getSpeakerlinksAsString());

                    stmt.bindString(19, mDatalist.get(i).getEnable_contacts());

                    stmt.bindString(20, mDatalist.get(i).getHideContactInfo());

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
     * Return list of category of all speakers
     * <p/>
     * Sample query
     * select distinct category from speakers where category !="" order by category COLLATE NOCASE;
     */
    public List<String> getCategoryList() {

        List<String> mList = new ArrayList<>();


        String query = "select distinct " + SpeakerTable.CATEGORY + " from " + SpeakerTable.SPEAKER_TABLE + " where " + SpeakerTable.CATEGORY + " !=\"\" order by " + SpeakerTable.CATEGORY + " COLLATE NOCASE;";

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
     * return list of speakes from database with limit of 20 speakers per hit
     *
     * @param isFirstName used to fetch speakers according ro firstName or lastName
     * @param i           used as offset for pagination
     *                    <p/>
     *                    Sample Query:
     *                    select * from speakers order by firstName COLLATE NOCASE limit 30 offset 30;
     *                    OR
     *                    select * from speakers order by lastName COLLATE NOCASE limit 30 offset 30;
     */
    public List<SpeakerData> getSpeakerList(boolean isFirstName, int i) {

        List<SpeakerData> mList = new ArrayList<>();

        String query;

//removed limit
//            if (isFirstName) {
//                query = "select * from " + SpeakerTable.ATTENDEE_TABLE + " order by " + SpeakerTable.FIRSTNAME + " COLLATE NOCASE limit 30 offset " + i * 30 + ";";
//            } else {
//                query = "select * from " + SpeakerTable.ATTENDEE_TABLE + " order by " + SpeakerTable.LASTNAME + " COLLATE NOCASE limit 30 offset " + i * 30 + ";";
//            }

        if (isFirstName) {
            query = "select * from " + SpeakerTable.SPEAKER_TABLE + " order by " + SpeakerTable.FIRSTNAME + " COLLATE NOCASE;";
        } else {
            query = "select * from " + SpeakerTable.SPEAKER_TABLE + " order by " + SpeakerTable.LASTNAME + " COLLATE NOCASE ;";
        }


        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                SpeakerData aData;
                do {
                    aData = new SpeakerData();

                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImageUrl(mCursor.getString(9));
                    aData.setLargeImageUrl(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setCategory(mCursor.getString(15));
                    aData.setSessionListAsString(mCursor.getString(16));
                    aData.setSpeakerlinksAsString(mCursor.getString(17));
                    aData.setEnable_contacts(mCursor.getString(18));
                    aData.setHideContactInfo(mCursor.getString(19));


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
     * Return list of speakers according to particular category
     *
     * @param isFirstName flag used to fetch attendees according ro firstName or lastName
     * @param category    provides category to which all attendees will belong.
     * @param i           used as offset for pagination
     *                    <p/>
     *                    Sample query
     *                    select * from attendees where category='server' order by firstname COLLATE NOCASE limit 30 offset 30;
     * @return list of Attendees belongs to particular category
     */
    public List<SpeakerData> sortSpeakerByCategory(boolean isFirstName, String category, int i) {

        List<SpeakerData> mList = new ArrayList<>();

        String query;
//        if(isFirstName){
//            query = " select * from "+SpeakerTable.ATTENDEE_TABLE+" where "+SpeakerTable.CATEGORY+"='"+category+"' order by "+SpeakerTable.FIRSTNAME+" COLLATE NOCASE limit 30 offset "+i*30+"";
//        }else{
//            query = " select * from "+SpeakerTable.ATTENDEE_TABLE+" where "+SpeakerTable.CATEGORY+"='"+category+"' order by "+SpeakerTable.LASTNAME+" COLLATE NOCASE limit 30 offset "+i*30+"";
//        }


        if (isFirstName) {
            query = " select * from " + SpeakerTable.SPEAKER_TABLE + " where " + SpeakerTable.CATEGORY + "='" + category + "' order by " + SpeakerTable.FIRSTNAME + " COLLATE NOCASE; ";
        } else {
            query = " select * from " + SpeakerTable.SPEAKER_TABLE + " where " + SpeakerTable.CATEGORY + "='" + category + "' order by " + SpeakerTable.LASTNAME + " COLLATE NOCASE; ";
        }


        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                SpeakerData aData;
                do {
                    aData = new SpeakerData();
                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImageUrl(mCursor.getString(9));
                    aData.setLargeImageUrl(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setCategory(mCursor.getString(15));
                    aData.setSessionListAsString(mCursor.getString(16));
                    aData.setSpeakerlinksAsString(mCursor.getString(17));
                    aData.setEnable_contacts(mCursor.getString(18));
                    aData.setHideContactInfo(mCursor.getString(19));

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
     * Fetch detail of speaker of particular id
     *
     * @param id id of Speaker whose detail we've to fetch from db.
     * @return object of Speaker
     */
    public SpeakerData getSpeakerDetail(String id) {

        openDB(0);

        SpeakerData aData = null;

        String query = " select * from " + SpeakerTable.SPEAKER_TABLE + " where " + SpeakerTable.ID + "='" + id + "';";

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                do {
                    aData = new SpeakerData();
                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImageUrl(mCursor.getString(9));
                    aData.setLargeImageUrl(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setCategory(mCursor.getString(15));
                    aData.setSessionListAsString(mCursor.getString(16));
                    aData.setSpeakerlinksAsString(mCursor.getString(17));
                    aData.setEnable_contacts(mCursor.getString(18));
                    aData.setHideContactInfo(mCursor.getString(19));

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
     * Return list of Speaker as user search Speaker by firstName or lastname
     *
     * @param category    name of category to which Speaker belong
     * @param queryStr    search query by user
     * @param isFirstName are names firstName sorted or lastName firstname.
     *                    <p>
     *                    Sample query
     *                    select * from attendees where  category='server' (firstName like 'ma%' or lastName like '') order by firstname COLLATE NOCASE;
     * @return list of all attendees which are returned by query
     */
    public List<SpeakerData> searchSpeakerByName(String category, String queryStr, boolean isFirstName) {


        List<SpeakerData> mList = new ArrayList<>();

        String query;

        if (category == null) {
            query = " select * from " + SpeakerTable.SPEAKER_TABLE + " where (" + SpeakerTable.FIRSTNAME + " like '" + queryStr + "%' or " + SpeakerTable.LASTNAME + " like '" + queryStr + "%') ";
        } else {
            query = " select * from " + SpeakerTable.SPEAKER_TABLE + " where " + SpeakerTable.CATEGORY + "='" + category + "' and (" + SpeakerTable.FIRSTNAME + " like '" + queryStr + "%' or " + SpeakerTable.LASTNAME + " like '" + queryStr + "%')";
        }


        if (isFirstName) {
            query += "order by " + SpeakerTable.FIRSTNAME + " COLLATE NOCASE;";
        } else {
            query += "order by " + SpeakerTable.LASTNAME + " COLLATE NOCASE;";
        }


//        if(isFirstName){
//            query = " select * from "+SpeakerTable.ATTENDEE_TABLE+" where  "+SpeakerTable.CATEGORY+"='"+category+"' ("+SpeakerTable.FIRSTNAME+" like '"+queryStr+"%' or "+SpeakerTable.LASTNAME+" like '"+queryStr+"%') order by "+SpeakerTable.FIRSTNAME+" COLLATE NOCASE;";
//        }else{
//            query = " select * from "+SpeakerTable.ATTENDEE_TABLE+" where  "+SpeakerTable.CATEGORY+"='"+category+"' ("+SpeakerTable.FIRSTNAME+" like '"+queryStr+"%' or "+SpeakerTable.LASTNAME+" like '"+queryStr+"%') order by "+SpeakerTable.LASTNAME+" COLLATE NOCASE;";
//        }

        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                SpeakerData aData;
                do {
                    aData = new SpeakerData();
                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImageUrl(mCursor.getString(9));
                    aData.setLargeImageUrl(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setCategory(mCursor.getString(15));
                    aData.setSessionListAsString(mCursor.getString(16));
                    aData.setSpeakerlinksAsString(mCursor.getString(17));
                    aData.setEnable_contacts(mCursor.getString(18));
                    aData.setHideContactInfo(mCursor.getString(19));

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


    public void deleteSpeakers() {

        openDB(1);
        try {
            db.delete(SpeakerTable.SPEAKER_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

    public String getSpeakerNames(String ids, SQLiteDatabase mDbInstance, boolean isFirstName) {

        String name = "";
        String idQuery = "";

        try {
            JSONArray mJsonArray = new JSONArray(ids);

            if (mJsonArray != null && mJsonArray.length() > 0) {

                for (int i = 0; i < mJsonArray.length(); i++) {
                    idQuery += "'" + mJsonArray.getString(i) + "',";
                }

                idQuery = idQuery.substring(0, idQuery.lastIndexOf(","));

                String query = "select firstName, lastName from speakers where id in(" + idQuery + ");";

                Cursor mcursor = null;
                try {
                    mcursor = mDbInstance.rawQuery(query, null);

                    if (mcursor != null) {
                        mcursor.moveToFirst();

                        do {

                            if (isFirstName) {
                                name += mcursor.getString(0) + " " + mcursor.getString(1) + ";";
                            } else {
                                name += mcursor.getString(1) + ", " + mcursor.getString(0) + ";";
                            }
                        } while (mcursor.moveToNext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    if (mcursor != null && !mcursor.isClosed()) {
                        mcursor.close();
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return name;
    }

}
