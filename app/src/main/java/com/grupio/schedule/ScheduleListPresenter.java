package com.grupio.schedule;

import android.content.Context;

import com.grupio.data.ScheduleData;

import java.util.List;

/**
 * Created by JSN on 7/11/16.
 */

public class ScheduleListPresenter implements ScheduleListContract.onInteraction, ScheduleListContract.Presenter {

    ScheduleListContract.View mListener;
    ScheduleListInteractor mInteractor;

    ScheduleListPresenter(ScheduleListContract.View mListener) {
        this.mListener = mListener;
        mInteractor = new ScheduleListInteractor();
    }

    @Override
    public void onListFetch(List<ScheduleData> mList) {
        mListener.showList(mList);
    }

    @Override
    public void onDateListFetch(List<String> mDateList) {
        mListener.showDate(mDateList);
    }

    @Override
    public void fetchList(String trackId, String searchQuery, String date, Context mContext) {
        mInteractor.fetchlist(trackId, searchQuery, date, mContext, this);
    }

    @Override
    public void fetchDateList(Context mContext) {
        mInteractor.fetchDateList(mContext, this);
    }
}
