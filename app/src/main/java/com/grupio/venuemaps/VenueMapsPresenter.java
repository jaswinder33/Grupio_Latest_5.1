package com.grupio.venuemaps;

import android.content.Context;

import java.util.List;

/**
 * Created by JSN on 24/10/16.
 */

public class VenueMapsPresenter<T> implements VenueMapContract.MapsPresenter, VenueMapContract.OnInteraction {

    private VenueMapContract.MapsView mListener;
    private VenueMapsInteractor mInteractor;

    VenueMapsPresenter(T t, VenueMapContract.MapsView mListener, Context mContext) {
        this.mListener = mListener;
        mInteractor = new VenueMapsInteractor();
        fetchList(t, mContext);
    }

    @Override
    public void onListFetch(List<?> mList) {
        mListener.showList(mList);
    }


    @Override
    public <T> void fetchList(T t, Context mContext) {
        mInteractor.fetchList(t, mContext, this);
    }
}
