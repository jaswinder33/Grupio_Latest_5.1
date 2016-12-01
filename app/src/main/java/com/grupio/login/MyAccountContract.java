package com.grupio.login;

import android.content.Context;

import com.grupio.data.AttendeesData;

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
    }

    interface Presenter {
        void fetchUserInfo(Context mContext);

        void updateImage(String url, Context mContext);
    }

    interface Interactor {
        void fetchUserInfo(Context mContext, OnInteraction mOnInteraction);

        void updateImage(String url, Context mContext, OnInteraction mOnInteraction);
    }

    interface OnInteraction {
        void onImageUpdated(String url);

        void showDetails(AttendeesData mData, boolean isFirstName);
    }
}
