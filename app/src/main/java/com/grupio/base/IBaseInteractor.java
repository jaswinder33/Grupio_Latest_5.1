package com.grupio.base;

import android.content.Context;

/**
 * Created by mani on 5/12/16.
 */

public interface IBaseInteractor {
    void showBanner(String bannerName, Context context, IBaseOnInteraction listener);

    void sendReport(String screenName, Context context, IBaseOnInteraction listener);
}
