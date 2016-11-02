package com.grupio.attendee.message;

import android.content.Context;

import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by JSN on 19/10/16.
 */

public class ChooseAttendeePresenter implements ChooseAttendeePresenterImp, ChooseAttendeeInteractorImp.onAttendeeInteraction {

    ChooseAttendeeView mListener;
    ChooseAttendeeInteractor mInteractor;

    public ChooseAttendeePresenter(ChooseAttendeeView mListener) {
        this.mListener = mListener;
        mInteractor = new ChooseAttendeeInteractor();
    }

    @Override
    public void fetchAttendeeList(Context mContext, String queryStr, String cateogory) {
        mInteractor.fetchAttendeeList(mContext, queryStr, cateogory, this);
    }

    @Override
    public void refreshAttendeeList(Context mContext) {
        mListener.showProgress();
        mInteractor.refreshAttendeeList(mContext, this);
    }

    @Override
    public void onFailure(String msg) {
        mListener.hideProgress();
    }

    @Override
    public void onAttendeeListFetch(List<AttendeesData> mList) {
        mListener.hideProgress();
        mListener.showAttendeeList(mList);
    }

}
