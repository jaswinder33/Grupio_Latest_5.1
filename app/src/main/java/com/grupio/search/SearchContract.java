package com.grupio.search;

import android.content.Context;

import com.grupio.base.BaseOnInteraction;
import com.grupio.base.IBaseInteractor;
import com.grupio.base.IBasePresenter;
import com.grupio.base.IBaseView;
import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by JSN on 7/12/16.
 */

public interface SearchContract {

    interface IViewI extends IBaseView {
        void showList(List<Person> mPerson);
    }

    interface IPresenterI extends IBasePresenter {
        void fetchData(Context mContext, String queryStr);
    }

    interface InteractorI extends IBaseInteractor {
        void fetchData(Context mContext, String queryStr, OnInteraction mListener);
    }

    interface OnInteraction extends BaseOnInteraction {
        void onListFetch(List<Person> mList);
    }
}
