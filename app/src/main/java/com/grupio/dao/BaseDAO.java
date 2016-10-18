package com.grupio.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.grupio.db.MyDbHandler;


/**
 * Created by JSN on 6/9/16.
 */
public class BaseDAO {

    protected Context mContext;
    protected SQLiteDatabase db;

    protected BaseDAO(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Open SQLiteDatabase(db) object according to need.
     *
     * @param type variable according to which SQLiteDatabase object is returned writable/readable object.
     * @param type == 0 it returns writable db object otherwise readable db object.
     */
    protected void openDB(int type) {
        db = MyDbHandler.getInstance(mContext).getDBObject(type);
    }

    protected void closeDb() {
        MyDbHandler.getInstance(mContext).closeDb();
        db.close();
    }


}
