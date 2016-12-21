package com.grupio.calendar;

import android.content.Context;

import com.grupio.base.IBaseInteractor;
import com.grupio.base.IBaseOnInteraction;
import com.grupio.base.IBasePresenter;
import com.grupio.base.IBaseView;
import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by JSN on 12/12/16.
 */

public interface CalendarListContract {

    interface IView extends IBaseView {
        void showList(List<Person> mList);

        void showDate(String date);
    }

    interface IPresenter extends IBasePresenter {

        void fetchListFromServer(Context context);

        void fetchList(Context context, String date);

        List<Person> getList(Context context, String date);

        List<String> getDateList(Context context);

        void refreshList(int counter, Context context);
    }

    interface IInteractor extends IBaseInteractor {

        void fetchListFromServer(Context context, IOnInteraction listener);


        void fetchList(Context context, String date, IOnInteraction listener);

        List<Person> getList(Context context, String date);

        List<String> getDataList(Context context);

        void refreshList(int counter, Context context, IOnInteraction listener);
    }

    interface IOnInteraction extends IBaseOnInteraction {
        void onListFetch(List<Person> mList);

        void showDate(String date);
    }
}
