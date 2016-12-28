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

        void goToNextScreen(MeetingData data);

        void showData(MeetingData data);
    }

    interface Presenter extends IBasePresenter {

        void showData(Context context, MeetingData data);

        void validateGoToNext(Context context, MeetingData data);

        void createMeeting(Context context, MeetingData data);

        void fetchTimeZone(Context context);

        MeetingData getMeetingData(Context context);
    }

    interface Interactor extends IBaseInteractor {
        void showData(Context context, MeetingData data, OnInteraction listener);

        void createMeeting(Context context, MeetingData data, OnInteraction listener);

        void fetchTimeZone(Context context, OnInteraction listener);

        MeetingData getMeetingData(Context context);
    }

    interface OnInteraction extends BaseOnInteraction {

        void onDataValidated(MeetingData data);

        void onMeetingCreated();

        void onTimeZone(String timezone);
    }
}
