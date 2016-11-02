package com.grupio.home;

import android.content.Context;

/**
 * Created by mani on 6/10/16.
 */

public interface HomeInteractorImp {

    void setHomeInfo(Context mContext, OnHomeInteractionComplete listener);

    String fetchLatitude(Context mContext);

    String fetchLongitude(Context mContext);

    String fetchEventWebsiteBtn(Context mContext);

    interface OnHomeInteractionComplete {
        void onWebLogoFetch(String url);

        void onEventDate(String date);

        void onEventName(String name);

        void onVenue(String venue);

        void onAddress(String address);

        void onStaticMap(String url);

        void onEventWebSiteBtn(String url);

        void onDescription(String description);
    }

}
