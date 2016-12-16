package com.grupio.calendar;

import android.content.Context;

import com.grupio.base.BasePresenter;
import com.grupio.interfaces.Person;

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
    public void fetchListFromServer(Context context) {
        getView().showProgress("Loading...");
        getInteractor().fetchListFromServer(context, this);
    }

    @Override
    public void fetchList(Context context, String date) {
        getInteractor().fetchList(context, date, this);
    }

    @Override
    public List<Person> getList(Context context, String date) {
        return getInteractor().getList(context, date);
    }

    @Override
    public List<String> getDateList(Context context) {
        return getInteractor().getDataList(context);
    }

    @Override
    public void onListFetch(List<Person> mList) {
        getView().hideProgress();
        getView().showList(mList);
    }
}
