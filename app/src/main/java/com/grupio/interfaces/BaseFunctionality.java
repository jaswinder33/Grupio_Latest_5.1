package com.grupio.interfaces;

import android.os.Bundle;
import android.text.TextWatcher;

import com.grupio.data.AdsData;

import java.util.List;

/**
 * Created by JSN on 13/10/16.
 */

public interface BaseFunctionality<Presenter> {

    void showProgressDialog(String msg);

    void hideProgressDialog();

    void showMessageDialog();

    void startBanner(String bannerName);

    void showBanner(List<AdsData> adsData);

    void sendReport(String screenName);

    String getScreenName();

    String getBannerName();

    Presenter getPresenter();

    Presenter setPresenter();

    void setupSearchBar(boolean showSearchBar, String locale);

    void addFilter(TextWatcher watcher);

    void init();

    void initIds();

    void registerListeners();

    void setListeners();

    void unRegisterListeners();

    int getLayout();

    void setUp();

    void goToNextScreen(Bundle bundle, Class<?> className);
}
