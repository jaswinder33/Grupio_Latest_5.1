package com.grupio.calendar;

import android.content.Context;

import com.grupio.base.IBaseInteractor;
import com.grupio.base.IBaseOnInteraction;
import com.grupio.base.IBasePresenter;
import com.grupio.base.IBaseView;
import com.grupio.data.AttendeesData;
import com.grupio.data.MeetingData;

import java.util.List;

/**
 * Created by JSN on 20/12/16.
 */

public interface MeetingDetailContract {

    interface View extends IBaseView {
        void showOrganizer(AttendeesData organizerData, boolean isFirstName);

        void showInvitieesList(List<AttendeesData> invitieesList);

        void onMeetingDeleted();

        void showMeetingData(MeetingData meetingData);

        void setDescription(String locale, String descriptionTxt);

        void setGraphics(String headerColor);

        void updateMeetingStatus(AttendeesData mAttendeeData, int attendeePosition, String status);

        void onMeetingStatusUpdated(int position, String status);
    }

    interface Presenter extends IBasePresenter {
        void fetchData(MeetingData meetingData, Context context);

        void deleteMeeting(MeetingData meetingData, Context context);

        void updateMeetingStatus(AttendeesData mAttendeeData, int attendeePosition, String status, Context context);

        MeetingData getMeetingData();
    }

    interface Interactor extends IBaseInteractor {
        void fetchData(MeetingData meetingData, Context context, OnInteraction listener);

        void deleteMeeting(MeetingData meetingData, Context context, OnInteraction listener);

        void updateMeetingStatus(AttendeesData mAttendeeData, int attendeePosition, String status, Context context, OnInteraction listener);

        MeetingData getMeetingData();
    }

    interface OnInteraction extends IBaseOnInteraction {
        void showOrganizer(AttendeesData organizerData, boolean isFirstName);

        void showInvitieeList(List<AttendeesData> invitieesList);

        void onDeleteMeeting();

        void setDescription(String locale, String descriptionTxt);

        void showMeetingData(MeetingData meetingData);

        void setGraphics(String headerColor);

        void onMeetingStatusUpdate(int position, String status);
    }


}
