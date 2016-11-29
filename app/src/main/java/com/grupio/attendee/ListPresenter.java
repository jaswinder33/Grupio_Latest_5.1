package com.grupio.attendee;

import android.content.Context;

import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by JSN on 25/7/16.
 */
public class ListPresenter<T extends Person> implements ListPresenterInter, ControllerInter.onTaskComplete {

    private ViewInter mListener;
    private ListController mController;
    private T type;

    public ListPresenter(T type, Context mContext, ViewInter mListener) {
        this.mListener = mListener;
        this.type = type;
        mController = new ListController(type, mContext);
        fetchCategoryList();
    }

    @Override
    public void fetchList(String queryStr, String cateogory) {
        if (mController != null) {
            mController.fetchListFromDB(queryStr, cateogory, this);
        }
    }

    @Override
    public void fetchListFromServer() {
        if (mListener != null) {
            mListener.showProgress();
        }

        if (mController != null) {
            mController.fetchListFromServer(this);
        }

    }

    @Override
    public void fetchCategoryList() {

        mListener.showProgress();

        if (mController != null) {
            mController.fetchCategoryList(this);
        }
    }

    @Override
    public <T> void fetchFavList(T type, String queryStr, String cateogory) {
        mController.fetchFavList(type, queryStr, cateogory, this);
    }


    @Override
    public void onListFetch(List<? extends Person> mlist) {
        mListener.hideProgress();
        if (mListener != null) {
            mListener.hideProgress();
            mListener.showList(mlist);
        }
    }

    @Override
    public void onCategoryFetch(List<String> mList) {
        if (mListener != null) {
            if (mList != null) {

                mListener.showCategory(mList);
            } else {
                mListener.hideCategory();
                fetchList(null, null);
            }
        }
    }

    @Override
    public void onFailure(String msg) {
        if (mListener != null) {
            mListener.hideProgress();
            mListener.onFailure(msg);
        }
    }

    @Override
    public void showFavLay() {
        mListener.showFavLay();
    }
}
