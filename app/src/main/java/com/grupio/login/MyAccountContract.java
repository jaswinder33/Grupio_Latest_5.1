package com.grupio.login;

import android.content.Context;

import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by mani on 30/11/16.
 */

public interface MyAccountContract {
    interface View {
        void showProgress(String msg);

        void hideProgress();

        void showImage(String url);

        void showDetails(AttendeesData mData, boolean isFirstName);

        void enableMessageSwitch();

        void enableHideProfileSwitch();

        void enableHideContact();

        void onProfileUpdate();

        void onFailure(String msg);

        void showInterest(List<String> mFullInterestList, List<String> mAttendeeInterest);
    }

    interface Presenter {
        void fetchUserInfo(Context mContext);

        void updateImage(String url, Context mContext);

        void updateUserInfo(AttendeesData mData, Context mContext);

        void fetchInterest(Context mContext);
    }

    interface Interactor {
        void fetchUserInfo(Context mContext, OnInteraction mOnInteraction);

        void updateImage(String url, Context mContext, OnInteraction mOnInteraction);

        void updateUserInfo(AttendeesData mData, Context mContext, OnInteraction mOnInteraction);

        void fetchInterest(Context mContext, OnInteraction mOnInteraction);
    }

    interface OnInteraction {
        void onImageUpdated(String url);

        void showDetails(AttendeesData mData, boolean isFirstName);

        void onUserInfoUpdation();

        void onFailure(String msg);

        void onInterestFetch(List<String> mFullInterestList, List<String> mAttendeeInterest);
    }
}
