package com.grupio.logistics;

import android.content.Context;

import com.grupio.data.LogisticsData;

import java.util.List;

/**
 * Created by mani on 21/10/16.
 */

public class LogisticsPresenter implements LogisticsContract.Presenter, LogisticsContract.OnInteraction {


    LogisticsContract.View mListener;
    LogisticsController mController;

    LogisticsPresenter(LogisticsContract.View mListener) {
        this.mListener = mListener;
        mController = new LogisticsController();
    }

    @Override
    public void onListFetch(List<LogisticsData> mData) {
        mListener.showList(mData);
    }

    @Override
    public void fetchList(Context mContext, String type) {
        mController.fetchList(mContext, type, this);
    }
}
