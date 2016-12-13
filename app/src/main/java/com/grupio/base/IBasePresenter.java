package com.grupio.base;

import android.content.Context;

/**
 * Created by JSN on 5/12/16.
 */

public interface IBasePresenter {
    void showBanner(String bannerName, Context context);

    void sendReport(String screenName, Context context);
}
