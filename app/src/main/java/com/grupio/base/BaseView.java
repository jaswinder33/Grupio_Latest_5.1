package com.grupio.base;

/**
 * Created by JSN on 5/12/16.
 */

public interface BaseView {
    void showProgress(String msg);

    void hideProgress();

    void onFailure(String msg);
}
