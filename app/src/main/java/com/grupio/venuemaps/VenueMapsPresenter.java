package com.grupio.venuemaps;

import android.content.Context;

import java.util.List;

/**
 * Created by JSN on 24/10/16.
 */

public class VenueMapsPresenter<T> implements VenueMapContract.MapsPresenter, VenueMapContract.OnInteraction {

    private VenueMapContract.MapsView mListener;
    private VenueMapsInteractor mInteractor;

    VenueMapsPresenter(VenueMapContract.MapsView mListener) {
        this.mListener = mListener;
        mInteractor = new VenueMapsInteractor();
    }


    @Override
    public void onMessageMarkedRead(String id) {
        mListener.hideProgress();
        mListener.onMessageMarked(id);
    }

    @Override
    public void onListFetch(List<?> mList) {
        mListener.hideProgress();
        mListener.showList(mList);
    }

    @Override
    public void onFailure(String msg) {
        mListener.hideProgress();
        mListener.onFailure(msg);
    }


    @Override
    public <T> void fetchList(T t, Context mContext) {
        mListener.showProgress();
        mInteractor.fetchList(t, mContext, this);
    }

    @Override
    public void markAlertRead(String alertId, Context mContext) {
        mListener.showProgress();
        mInteractor.markAlertRead(alertId, mContext, this);
    }
}
