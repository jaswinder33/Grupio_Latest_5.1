package com.grupio.base;

import com.grupio.data.AdsData;

import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */
public interface IBaseView {
    void showProgress(String msg);

    void hideProgress();

    void onFailure(String msg);

    void showBanner(List<AdsData> adsData);
}
