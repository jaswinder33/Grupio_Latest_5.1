package com.grupio.venuemaps;

import android.content.Context;

import java.util.List;

/**
 * Created by JSN on 24/10/16.
 */

public interface VenueMapContract {

    interface MapsView {
        void showList(List<?> mList);
    }

    interface MapsPresenter {
        <T> void fetchList(T t, Context mContext);
    }

    interface MapsInteractor {
        <T> void fetchList(T t, Context mContext, OnInteraction mlistener);
    }

    interface OnInteraction {
        void onListFetch(List<?> mList);
    }
}
