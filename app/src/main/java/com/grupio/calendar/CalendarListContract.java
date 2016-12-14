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
    }

    interface IPresenter extends IBasePresenter {
        void fetchList(Context context, String date);

        List<Person> getList(Context context, String date);

        List<String> getDataList(Context context);
    }

    interface IInteractor extends IBaseInteractor {
        void fetchList(Context context, String date, IOnInteraction listener);

        List<Person> getList(Context context, String date);

        List<String> getDataList(Context context);
    }

    interface IOnInteraction extends IBaseOnInteraction {
        void onListFetch(List<Person> mList);
    }
}
