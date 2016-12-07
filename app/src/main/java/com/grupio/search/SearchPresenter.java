package com.grupio.search;

import android.content.Context;

import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by mani on 7/12/16.
 */

public class SearchPresenter implements SearchContract.IPresenter, SearchContract.OnInteraction {

    private SearchContract.IView mListener;
    private SearchInteractor mInteractor;

    public SearchPresenter(SearchContract.IView mListener) {
        this.mListener = mListener;
        mInteractor = new SearchInteractor();
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
