package com.grupio.venuemaps;

import android.content.Context;

import com.grupio.dao.MapsDAO;
import com.grupio.data.MapsData;

/**
 * Created by JSN on 24/10/16.
 */

public class VenueMapsInteractor implements VenueMapContract.MapsInteractor {

    @Override
    public <T> void fetchList(T t, Context mContext, VenueMapContract.OnInteraction mlistener) {
        if (t instanceof MapsData) {
            mlistener.onListFetch(MapsDAO.getInstance(mContext).getMapList());
        }
    }
}
