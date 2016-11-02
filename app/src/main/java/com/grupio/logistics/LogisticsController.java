package com.grupio.logistics;

import android.content.Context;

import com.grupio.dao.LogisticsDAO;

/**
 * Created by JSN on 21/10/16.
 */

public class LogisticsController implements LogisticsContract.Interactor {


    @Override
    public void fetchList(Context mContext, String type, LogisticsContract.OnInteraction mListener) {
        mListener.onListFetch(LogisticsDAO.getInstance(mContext).getLogistics(type));
    }
}
