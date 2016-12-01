package com.grupio.login;

import android.content.Context;

import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by JSN on 30/11/16.
 */

public class MyAccountPresenter implements MyAccountContract.Presenter, MyAccountContract.OnInteraction {

    private MyAccountContract.View mListener;
    private MyAccountInteractor mInteractor;

    public MyAccountPresenter(MyAccountContract.View mListener) {
        this.mListener = mListener;
        mInteractor = new MyAccountInteractor();
    }

    @Override
    public void fetchUserInfo(Context mContext) {
        mInteractor.fetchUserInfo(mContext, this);
    }

    @Override
    public void updateImage(String url, Context mContext) {
        mInteractor.updateImage(url, mContext, this);
    }

    @Override
    public void updateUserInfo(AttendeesData mData, Context mContext) {
        mListener.showProgress("Updating Info...");
        mInteractor.updateUserInfo(mData, mContext, this);
    }

    @Override
    public void fetchInterest(Context mContext) {
        mListener.showProgress("Fetching Interest...");
        mInteractor.fetchInterest(mContext, this);
    }

    @Override
    public void onImageUpdated(String url) {
        mListener.showImage(url);
    }

    @Override
    public void showDetails(AttendeesData mData, boolean isFirstName) {
        mListener.showDetails(mData, isFirstName);

        if (mData.getEnable_messaging().equals("y")) {
            mListener.enableMessageSwitch();
        }

        if (mData.getHideMe().equals("y")) {
            mListener.enableHideProfileSwitch();
        }

        if (mData.getHide_contact_info().equals("y")) {
            mListener.enableHideContact();
        }
    }

    @Override
    public void onUserInfoUpdation() {
        mListener.hideProgress();
        mListener.onProfileUpdate();
    }

    @Override
    public void onFailure(String msg) {
        mListener.onFailure(msg);
    }

    @Override
    public void onInterestFetch(List<String> mFullInterestList, List<String> mAttendeeInterest) {
        mListener.hideProgress();
        mListener.showInterest(mFullInterestList, mAttendeeInterest);
    }
}
