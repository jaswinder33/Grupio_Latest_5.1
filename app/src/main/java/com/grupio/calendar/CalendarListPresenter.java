package com.grupio.calendar;

import android.content.Context;

import com.grupio.base.BasePresenter;

import java.util.List;

/**
 * Created by JSN on 12/12/16.
 */

public class CalendarListPresenter extends BasePresenter<CalendarListContract.IView, CalendarListContract.IInteractor> implements CalendarListContract.IPresenter, CalendarListContract.IOnInteraction {

    public CalendarListPresenter(CalendarListContract.IView view) {
        super(view);
        setInteractor(new CalendarListInteractor());
    }

    @Override
    public void fetchList(Context context) {
        getView().showProgress("");
        getInteractor().fetchList(context, this);
    }

    @Override
    public <T> void showList(List<T> mList) {
        getView().hideProgress();
        getView().showList(mList);

    }
}