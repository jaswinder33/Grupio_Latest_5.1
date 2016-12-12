package com.grupio.dao;

import android.content.Context;

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

    public void insert(String response) {


    }
}
