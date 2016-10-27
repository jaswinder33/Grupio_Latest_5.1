package com.grupio.venuemaps;

import com.grupio.activities.BaseActivity;

/**
 * Created by JSN on 27/10/16.
 */

public class SimpleListBaseActivity<T> extends BaseActivity<T> {

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public void handleRightBtnClick() {

    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }

    @Override
    public void initIds() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public T setPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void setUp() {

    }
}
