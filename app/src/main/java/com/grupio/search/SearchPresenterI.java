package com.grupio.search;

import android.content.Context;

import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by mani on 7/12/16.
 */

public class SearchPresenterI implements SearchContract.IPresenterI, SearchContract.OnInteraction {

    private SearchContract.IViewI mListener;
    private SearchInteractorI mInteractor;

    public SearchPresenterI(SearchContract.IViewI mListener) {
        this.mListener = mListener;
        mInteractor = new SearchInteractorI();
    }

    @Override
    public void onFailure(String msg) {
        mListener.onFailure(msg);
    }

    @Override
    public void fetchData(Context mContext, String queryStr) {
        mInteractor.fetchData(mContext, queryStr, this);
    }

    @Override
    public void onListFetch(List<Person> mList) {
        mListener.showList(mList);
    }
}
