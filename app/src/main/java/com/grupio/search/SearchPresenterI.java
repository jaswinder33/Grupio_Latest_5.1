package com.grupio.search;

import android.content.Context;

import com.grupio.base.BasePresenter;
import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by mani on 7/12/16.
 */

public class SearchPresenterI extends BasePresenter<SearchContract.IViewI, SearchContract.InteractorI> implements SearchContract.IPresenterI, SearchContract.OnInteraction {

    public SearchPresenterI(SearchContract.IViewI view) {
        super(view);
        setInteractor(new SearchInteractorI());
    }

    @Override
    public void onFailure(String msg) {
        getView().onFailure(msg);
    }

    @Override
    public void fetchData(Context mContext, String queryStr) {
        getInteractor().fetchData(mContext, queryStr, this);
    }

    @Override
    public void onListFetch(List<Person> mList) {
        getView().showList(mList);
    }
}
