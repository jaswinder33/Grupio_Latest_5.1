package com.grupio.home;

import android.content.Context;

/**
 * Created by JSN on 5/10/16.
 */
public interface HomePresenterImp {
    void setHomeInfo(Context mcontext);

    void openMap(Context mContext);

    void getDirections(Context mContext);

    void openEventWebsite(Context mContext);
}
