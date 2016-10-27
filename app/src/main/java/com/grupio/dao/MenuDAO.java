package com.grupio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

import com.grupio.data.MenuData;
import com.grupio.db.MenuTable;
import com.grupio.helper.MenuProcessor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JSN on 22/8/16.
 */
public class MenuDAO extends BaseDAO {

    private static MenuDAO mMenuDAO;

    private MenuDAO(Context mContext) {
        super(mContext);
    }

    public static MenuDAO getInstance(Context mContext) {
        if (mMenuDAO == null) {
            mMenuDAO = new MenuDAO(mContext);
        }
        return mMenuDAO;
    }

    public void insertData(String result) {

        deleteMenus();

        List<MenuData> mDatalist = new ArrayList<>();

        MenuProcessor menuProcessor = new MenuProcessor();
        mDatalist.addAll(menuProcessor.processResult(result, mContext));

        openDB(1);


        SQLiteStatement stmt = null;

        db.beginTransaction();

        try {
            stmt = db.compileStatement("INSERT INTO " + MenuTable.MENU_TABLE
                    + " ( "
                    + MenuTable.MENU_NAME + ","
                    + MenuTable.MENU_ORDER + ","
                    + MenuTable.DISPLAY_NAME + ","
                    + MenuTable.ICON_URL + ") VALUES(?,?,?,?)"

            );

            if (mDatalist != null && mDatalist.size() > 0) {

                for (int i = 0; i < mDatalist.size(); i++) {
                    stmt.bindString(1, mDatalist.get(i).getMenuName());
                    stmt.bindString(2, mDatalist.get(i).getMenuOrder());
                    stmt.bindString(3, mDatalist.get(i).getmDisplayName());
                    stmt.bindString(4, mDatalist.get(i).getMenuIconUrl());

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


    public List<MenuData> fetchMenu() {

        List<MenuData> mDataList = new ArrayList<>();
        MenuData mData;

        openDB(0);

        String query = "Select * from " + MenuTable.MENU_TABLE + " order by cast(" + MenuTable.MENU_ORDER + " as Integer);";

        Cursor mCursor = db.rawQuery(query, null);

        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                mData = new MenuData();

                mData.setMenuName(mCursor.getString(0));
                mData.setMenuOrder(mCursor.getString(1));
                mData.setmDisplayName(mCursor.getString(2));
                mData.setMenuIconUrl(mCursor.getString(3));
                mDataList.add(mData);

            } while (mCursor.moveToNext());

        }

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        closeDb();

        return mDataList;

    }

    public void deleteMenus() {
        openDB(1);
        try {
            db.delete(MenuTable.MENU_TABLE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                closeDb();
            }
        }
    }

    public boolean checkIfMenuExists(String menuName) {

        boolean flag = false;

        openDB(0);
        String query = "select exists (select  * from " + MenuTable.MENU_TABLE + " where " + MenuTable.MENU_NAME + " = '" + menuName + "' );";

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(query, null);

            if (mCursor != null && mCursor.moveToFirst()) {
                flag = mCursor.getInt(0) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }

            closeDb();
        }


        return flag;
    }
}
