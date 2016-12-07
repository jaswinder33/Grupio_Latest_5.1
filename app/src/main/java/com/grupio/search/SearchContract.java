package com.grupio.search;

import android.content.Context;

import com.grupio.base.BaseInteractor;
import com.grupio.base.BaseOnInteraction;
import com.grupio.base.BasePresenter;
import com.grupio.base.BaseView;
import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by JSN on 7/12/16.
 */

public interface SearchContract {

    interface IView extends BaseView {
        void showList(List<Person> mPerson);
    }

    interface IPresenter extends BasePresenter {
        void fetchData(Context mContext, String queryStr);
    }

    interface Interactor extends BaseInteractor {
        void fetchData(Context mContext, String queryStr, OnInteraction mListener);
    }

    interface OnInteraction extends BaseOnInteraction {
        void onListFetch(List<Person> mList);
    }
}
