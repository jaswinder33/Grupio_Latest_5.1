package com.grupio.calendar;

import com.grupio.fragments.BaseFragment;

/**
 * Created by JSN on 12/12/16.
 */

public class CalendarFragment extends BaseFragment<CalendarListPresenter> implements CalendarListContract.IView {
    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initIds() {
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setUp() {

    }

    @Override
    public CalendarListPresenter setPresenter() {
        return new CalendarListPresenter(this);
    }

    @Override
    public String getScreenName() {
        return "MEETING_LIST_VIEW";
    }

    @Override
    public String getBannerName() {
        return "mycalendar";
    }

    @Override
    public void showProgress(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }
}
