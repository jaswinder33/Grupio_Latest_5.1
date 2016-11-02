package com.grupio.venuemaps;

import android.content.Context;

import java.util.List;

/**
 * Created by JSN on 24/10/16.
 */

public interface VenueMapContract {

    interface MapsView {
        void showList(List<?> mList);

        void onFailure(String msg);

        void showProgress();

        void hideProgress();

        void onMessageMarked(String id);
    }

    interface MapsPresenter {
        <T> void fetchList(T t, Context mContext);

        void markAlertRead(String alertId, Context mContext);
    }

    interface MapsInteractor {
        void markAlertRead(String alertId, Context mContext, OnInteraction mlistener);

        <T> void fetchList(T t, Context mContext, OnInteraction mlistener);
    }

    interface OnInteraction {
        void onMessageMarkedRead(String id);

        void onListFetch(List<?> mList);

        void onFailure(String msg);
    }
}
