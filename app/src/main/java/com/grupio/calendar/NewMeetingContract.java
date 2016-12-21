package com.grupio.calendar;

import android.content.Context;

import com.grupio.base.BaseOnInteraction;
import com.grupio.base.IBaseInteractor;
import com.grupio.base.IBasePresenter;
import com.grupio.base.IBaseView;
import com.grupio.data.MeetingData;

/**
 * Created by mani on 19/12/16.
 */

public interface NewMeetingContract {

    interface View extends IBaseView {
        void showErrorMessage(String msg);

        void showTimeZone(String timezone);

        void goToNextScreen();
    }

    interface Presenter extends IBasePresenter {
        void validateGoToNext(Context context, MeetingData data);

        void createMeeting(Context context, MeetingData data);

        void fetchTimeZone(Context context);
    }

    interface Interactor extends IBaseInteractor {
        void createMeeting(Context context, MeetingData data, OnInteraction listener);

        void fetchTimeZone(Context context, OnInteraction listener);
    }

    interface OnInteraction extends BaseOnInteraction {
        void onMeetingCreated();

        void onTimeZone(String timezone);
    }
}
