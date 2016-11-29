package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.AttendeesData;
import com.grupio.db.AttendeeTable;
import com.grupio.helper.AttendeeProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 26/7/16.
 */
public class AttendeeDAO extends BaseDAO {

    //AttendeeDAO object
    private static AttendeeDAO mAttendeeDAO;

    /**
     * private constructor
     */
    private AttendeeDAO(Context mContext) {
        super(mContext);
    }

    /**
     * Returns instance of class
     *
     * @return
     */
    public static AttendeeDAO getInstance(Context mContext) {
        if (mAttendeeDAO == null) {
            mAttendeeDAO = new AttendeeDAO(mContext);
        }
        return mAttendeeDAO;
    }

    /**
     * insert attendee data directly from API in db
     */
    public void insert(String responseFromAPI) {

        deleteAttendees();

        List<AttendeesData> mDatalist = new ArrayList<>();
        AttendeeProcessor ap = new AttendeeProcessor();
        mDatalist.addAll(ap.getAttendeesListFromJSON(mContext, responseFromAPI, true));

        openDB(1);


        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + AttendeeTable.ATTENDEE_TABLE
                    + " ( "
                    + AttendeeTable.ID + ","
                    + AttendeeTable.FIRSTNAME + ","
                    + AttendeeTable.LASTNAME + ","

                    + AttendeeTable.COMPANY + ","
                    + AttendeeTable.TITLE + ","
                    + AttendeeTable.EMAIL + ","

                    + AttendeeTable.TYPE + ","
                    + AttendeeTable.PRIMARYPHONE + ","
                    + AttendeeTable.SECONDARYPHONE + ","

                    + AttendeeTable.IMAGE + ","
                    + AttendeeTable.LARGEIMAGE + ","

                    + AttendeeTable.BIO + ","
                    + AttendeeTable.LINKEDINID + ","
                    + AttendeeTable.TWITTERID + ","
                    + AttendeeTable.WEBSITE + ","

                    + AttendeeTable.INTEREST + ","
                    + AttendeeTable.CATEGORY + ","
                    + AttendeeTable.KEYWORDS + ","

                    + AttendeeTable.SESSIONLIST + ","
                    + AttendeeTable.RESOURCELINKS + ","

                    + AttendeeTable.ENABLECONTACTS + ","
                    + AttendeeTable.ENABLEMESSAGING + ","

                    + AttendeeTable.HIDECONTACTINFO + ","
                    + AttendeeTable.ISADMIN + ","
                    + AttendeeTable.ENABLEMATCH + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

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

                    stmt.bindString(10, mDatalist.get(i).getImage());
                    stmt.bindString(11, mDatalist.get(i).getLarge_image());

                    stmt.bindString(12, mDatalist.get(i).getBio());
                    stmt.bindString(13, mDatalist.get(i).getLinkedin());
                    stmt.bindString(14, mDatalist.get(i).getTwitter_id());
                    stmt.bindString(15, mDatalist.get(i).getWebsite());

                    stmt.bindString(16, mDatalist.get(i).getIntrests());
                    stmt.bindString(17, mDatalist.get(i).getCategory());
                    stmt.bindString(18, mDatalist.get(i).getKeywords());

                    stmt.bindString(19, mDatalist.get(i).sessionsAsString);
                    stmt.bindString(20, mDatalist.get(i).mapListOfAttendeeAsString);

                    stmt.bindString(21, mDatalist.get(i).getEnable_contacts());
                    stmt.bindString(22, mDatalist.get(i).getEnable_messaging());

                    stmt.bindString(23, mDatalist.get(i).getHide_contact_info());
                    stmt.bindString(24, "0");
                    stmt.bindString(25, mDatalist.get(i).getEnable_match());

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


    /**
     * Return list of category of all attendees
     * <p/>
     * Sample query
     * select distinct category from attendees where category !="" order by category COLLATE NOCASE;
     */
    public List<String> getCategoryList() {

        List<String> mList = new ArrayList<>();

        String query = "select distinct " + AttendeeTable.CATEGORY + " from " + AttendeeTable.ATTENDEE_TABLE + " where " + AttendeeTable.CATEGORY + " !=\"\" order by " + AttendeeTable.CATEGORY + " COLLATE NOCASE;";

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
     * return list of attendees from database with limit of 20 attendees per hit
     *
     * @param isFirstName used to fetch attendees according ro firstName or lastName
     * @param i           used as offset for pagination
     *                    <p/>
     *                    Sample Query:
     *                    select * from attendees order by firstName COLLATE NOCASE limit 30 offset 30;
     *                    OR
     *                    select * from attendees order by lastName COLLATE NOCASE limit 30 offset 30;
     */
    public List<AttendeesData> getAttendeeList(boolean isFirstName, int i) {

        List<AttendeesData> mList = new ArrayList<>();

        String query;

//removed limit
//            if (isFirstName) {
//                query = "select * from " + AttendeeTable.ATTENDEE_TABLE + " order by " + AttendeeTable.FIRSTNAME + " COLLATE NOCASE limit 30 offset " + i * 30 + ";";
//            } else {
//                query = "select * from " + AttendeeTable.ATTENDEE_TABLE + " order by " + AttendeeTable.LASTNAME + " COLLATE NOCASE limit 30 offset " + i * 30 + ";";
//            }

        if (isFirstName) {
            query = "select * from " + AttendeeTable.ATTENDEE_TABLE + " order by " + AttendeeTable.FIRSTNAME + " COLLATE NOCASE;";
        } else {
            query = "select * from " + AttendeeTable.ATTENDEE_TABLE + " order by " + AttendeeTable.LASTNAME + " COLLATE NOCASE ;";
        }


        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                AttendeesData aData;
                do {
                    aData = new AttendeesData();

                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImage(mCursor.getString(9));
                    aData.setLarge_image(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setIntrests(mCursor.getString(15));
                    aData.setCategory(mCursor.getString(16));
                    aData.setKeywords(mCursor.getString(17));
                    aData.sessionsAsString = mCursor.getString(18);
                    aData.mapListOfAttendeeAsString = mCursor.getString(19);
                    aData.setEnable_contacts(mCursor.getString(20));
                    aData.setEnable_messaging(mCursor.getString(21));
                    aData.setHide_contact_info(mCursor.getString(22));
                    aData.setEnable_match(mCursor.getString(24));

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
     * Return list of attendees according to particular category
     *
     * @param isFirstName flag used to fetch attendees according ro firstName or lastName
     * @param category    provides category to which all attendees will belong.
     * @param i           used as offset for pagination
     *                    <p/>
     *                    Sample query
     *                    select * from attendees where category='server' order by firstname COLLATE NOCASE limit 30 offset 30;
     * @return list of Attendees belongs to particular category
     */
    public List<AttendeesData> sortAttendeeByCategory(boolean isFirstName, String category, int i) {

        List<AttendeesData> mList = new ArrayList<>();

        String query;
//        if(isFirstName){
//            query = " select * from "+AttendeeTable.ATTENDEE_TABLE+" where "+AttendeeTable.CATEGORY+"='"+category+"' order by "+AttendeeTable.FIRSTNAME+" COLLATE NOCASE limit 30 offset "+i*30+"";
//        }else{
//            query = " select * from "+AttendeeTable.ATTENDEE_TABLE+" where "+AttendeeTable.CATEGORY+"='"+category+"' order by "+AttendeeTable.LASTNAME+" COLLATE NOCASE limit 30 offset "+i*30+"";
//        }


        if (isFirstName) {
            query = " select * from " + AttendeeTable.ATTENDEE_TABLE + " where " + AttendeeTable.CATEGORY + "='" + category + "' order by " + AttendeeTable.FIRSTNAME + " COLLATE NOCASE; ";
        } else {
            query = " select * from " + AttendeeTable.ATTENDEE_TABLE + " where " + AttendeeTable.CATEGORY + "='" + category + "' order by " + AttendeeTable.LASTNAME + " COLLATE NOCASE; ";
        }


        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                AttendeesData aData;
                do {
                    aData = new AttendeesData();
                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImage(mCursor.getString(9));
                    aData.setLarge_image(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setIntrests(mCursor.getString(15));
                    aData.setCategory(mCursor.getString(16));
                    aData.setKeywords(mCursor.getString(17));
                    aData.sessionsAsString = mCursor.getString(18);
                    aData.mapListOfAttendeeAsString = mCursor.getString(19);
                    aData.setEnable_contacts(mCursor.getString(20));
                    aData.setEnable_messaging(mCursor.getString(21));
                    aData.setHide_contact_info(mCursor.getString(22));
                    aData.setEnable_match(mCursor.getString(24));

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
     * Fetch detail of attendee of particular id
     *
     * @param id id of attendee whose detail we've to fetch from db.
     * @return object of Attendee
     */
    public AttendeesData getAttendeeDetal(String id) {

        openDB(0);

        AttendeesData aData = null;

        String query = " select * from " + AttendeeTable.ATTENDEE_TABLE + " where " + AttendeeTable.ID + "='" + id + "';";

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                do {
                    aData = new AttendeesData();
                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImage(mCursor.getString(9));
                    aData.setLarge_image(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setIntrests(mCursor.getString(15));
                    aData.setCategory(mCursor.getString(16));
                    aData.setKeywords(mCursor.getString(17));
                    aData.sessionsAsString = mCursor.getString(18);
                    aData.mapListOfAttendeeAsString = mCursor.getString(19);
                    aData.setEnable_contacts(mCursor.getString(20));
                    aData.setEnable_messaging(mCursor.getString(21));
                    aData.setHide_contact_info(mCursor.getString(22));
                    aData.setEnable_match(mCursor.getString(24));

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
     * Return list of attendees as user search attendees by firstName or lastname
     *
     * @param category    name of category to which attendees belong
     * @param queryStr    search query by user
     * @param isFirstName are names firstName sorted or lastName firstname.
     *                    <p/>
     *                    Sample query
     *                    select * from attendees where  category='server' (firstName like 'ma%' or lastName like '') order by firstname COLLATE NOCASE;
     * @return list of all attendees which are returned by query
     */
    public List<AttendeesData> searchAttendeeByName(String category, String queryStr, boolean isFirstName) {


        List<AttendeesData> mList = new ArrayList<>();

        String query;

        if (category == null) {
            query = " select * from " + AttendeeTable.ATTENDEE_TABLE + " where (" + AttendeeTable.FIRSTNAME + " like '" + queryStr + "%' or " + AttendeeTable.LASTNAME + " like '" + queryStr + "%') ";
        } else {
            query = " select * from " + AttendeeTable.ATTENDEE_TABLE + " where " + AttendeeTable.CATEGORY + "='" + category + "' and (" + AttendeeTable.FIRSTNAME + " like '" + queryStr + "%' or " + AttendeeTable.LASTNAME + " like '" + queryStr + "%')";
        }


        if (isFirstName) {
            query += "order by " + AttendeeTable.FIRSTNAME + " COLLATE NOCASE;";
        } else {
            query += "order by " + AttendeeTable.LASTNAME + " COLLATE NOCASE;";
        }


//        if(isFirstName){
//            query = " select * from "+AttendeeTable.ATTENDEE_TABLE+" where  "+AttendeeTable.CATEGORY+"='"+category+"' ("+AttendeeTable.FIRSTNAME+" like '"+queryStr+"%' or "+AttendeeTable.LASTNAME+" like '"+queryStr+"%') order by "+AttendeeTable.FIRSTNAME+" COLLATE NOCASE;";
//        }else{
//            query = " select * from "+AttendeeTable.ATTENDEE_TABLE+" where  "+AttendeeTable.CATEGORY+"='"+category+"' ("+AttendeeTable.FIRSTNAME+" like '"+queryStr+"%' or "+AttendeeTable.LASTNAME+" like '"+queryStr+"%') order by "+AttendeeTable.LASTNAME+" COLLATE NOCASE;";
//        }

        openDB(0);

        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                AttendeesData aData;
                do {
                    aData = new AttendeesData();
                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImage(mCursor.getString(9));
                    aData.setLarge_image(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setIntrests(mCursor.getString(15));
                    aData.setCategory(mCursor.getString(16));
                    aData.setKeywords(mCursor.getString(17));
                    aData.sessionsAsString = mCursor.getString(18);
                    aData.mapListOfAttendeeAsString = mCursor.getString(19);
                    aData.setEnable_contacts(mCursor.getString(20));
                    aData.setEnable_messaging(mCursor.getString(21));
                    aData.setHide_contact_info(mCursor.getString(22));
                    aData.setEnable_match(mCursor.getString(24));

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

    public List<AttendeesData> fetchAttendeesOfParticularids(List<String> idList) {

        List<AttendeesData> mList = new ArrayList<>();

        openDB(0);

        String query = "select * from " + AttendeeTable.ATTENDEE_TABLE + " where " + AttendeeTable.ID + " IN (";


        String ids = "";
        for (int i = 0; i < idList.size(); i++) {
            ids += idList.get(i) + ",";
        }

        ids = ids.substring(0, ids.length() - 1);

//        String ids="";
//        for(int i=0; i<idList.size();i++){
//            if(i == 0){
//                ids = "(" + idList.get(i)  + ",";
//            }else if( i == idList.size()-1){
//                ids += idList.get(i) + ");";
//            }else {
//                ids += idList.get(i) + ",";
//            }
//        }

        query += ids + ");";


        Cursor mCursor = null;

        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor.moveToFirst()) {

                AttendeesData aData;
                do {
                    aData = new AttendeesData();
                    aData.setAttendee_id(mCursor.getString(0));
                    aData.setFirst_name(mCursor.getString(1));
                    aData.setLast_name(mCursor.getString(2));
                    aData.setCompany(mCursor.getString(3));
                    aData.setTitle(mCursor.getString(4));
                    aData.setEmail(mCursor.getString(5));
                    aData.setType(mCursor.getString(6));
                    aData.setPrimary_phone(mCursor.getString(7));
                    aData.setSecondary_phone(mCursor.getString(8));
                    aData.setImage(mCursor.getString(9));
                    aData.setLarge_image(mCursor.getString(10));
                    aData.setBio(mCursor.getString(11));
                    aData.setLinkedin(mCursor.getString(12));
                    aData.setTwitter_id(mCursor.getString(13));
                    aData.setWebsite(mCursor.getString(14));
                    aData.setIntrests(mCursor.getString(15));
                    aData.setCategory(mCursor.getString(16));
                    aData.setKeywords(mCursor.getString(17));
                    aData.sessionsAsString = mCursor.getString(18);
                    aData.mapListOfAttendeeAsString = mCursor.getString(19);
                    aData.setEnable_contacts(mCursor.getString(20));
                    aData.setEnable_messaging(mCursor.getString(21));
                    aData.setHide_contact_info(mCursor.getString(22));
                    aData.setEnable_match(mCursor.getString(24));

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


    public void deleteAttendees() {

        openDB(0);
        try {
            db.delete(AttendeeTable.ATTENDEE_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

    public String getValue(String columnName, String attendeeId) {

        openDB(0);

        String value = "";
        String query = "select " + columnName + " from " + AttendeeTable.ATTENDEE_TABLE + " where " + AttendeeTable.ID + "=" + attendeeId;

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);
            if (mCursor != null && mCursor.moveToFirst()) {
                value = mCursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            closeDb();
        }

        return value;
    }

}
