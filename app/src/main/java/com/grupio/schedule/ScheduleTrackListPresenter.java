package com.grupio.schedule;

import android.content.Context;

import com.grupio.data.TrackData;

import java.util.List;

/**
 * Created by JSN on 27/10/16.
 */

public class ScheduleTrackListPresenter implements ScheduleTrackListContract.SchedulePresenterImp, ScheduleTrackListContract.ScheduleOnInteraction {

    ScheduleTrackListContract.ScheduleView mListener;
    ScheduleTrackListInteractor mInteractor;

    ScheduleTrackListPresenter(ScheduleTrackListContract.ScheduleView mListener) {
        this.mListener = mListener;
        mInteractor = new ScheduleTrackListInteractor();
    }

    @Override
    public void onListFetch(List<TrackData> mList) {
        mListener.showList(mList);
    }

    @Override
    public void fetchList(Context mContext) {
        mInteractor.fetchList(mContext, this);
    }
}
