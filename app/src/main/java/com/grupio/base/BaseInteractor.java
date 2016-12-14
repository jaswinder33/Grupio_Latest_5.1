package com.grupio.base;

import android.content.Context;

import com.grupio.dao.AdsDAO;
import com.grupio.data.AdsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 12/12/16.
 */
public class BaseInteractor implements IBaseInteractor {
    @Override
    public void showBanner(String bannerName, Context context, IBaseOnInteraction listener) {
        if (bannerName != null) {

            List<AdsData> mList = new ArrayList<>();
            mList.addAll(AdsDAO.getInstance(context).showBanner(bannerName));

            if (!mList.isEmpty()) {
                listener.onShowBanner(mList);
            }
        }
    }

    @Override
    public void sendReport(String screenName, Context context, IBaseOnInteraction listener) {
        if (screenName != null) {

        }

    }
}
