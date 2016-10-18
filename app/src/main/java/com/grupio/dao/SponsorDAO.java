package com.grupio.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.grupio.db.MyDbHandler;


/**
 * Created by JSN on 1/9/16.
 */
public class SponsorDAO extends BaseDAO{

    private SponsorDAO(Context mContext){
        super(mContext);
        }

    public static SponsorDAO getInstance(Context mContext){ return new SponsorDAO(mContext);}

    public void insertData(){

    }

    public void deleteData(){

    }

}
