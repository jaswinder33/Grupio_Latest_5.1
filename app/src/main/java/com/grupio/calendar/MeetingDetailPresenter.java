package com.grupio.calendar;

import android.content.Context;

import com.grupio.base.BasePresenter;
import com.grupio.data.AttendeesData;
import com.grupio.data.MeetingData;

import java.util.List;

/**
 * Created by JSN on 20/12/16.
 */

public class MeetingDetailPresenter extends BasePresenter<MeetingDetailContract.View, MeetingDetailContract.Interactor> implements MeetingDetailContract.Presenter, MeetingDetailContract.OnInteraction {

    public MeetingDetailPresenter(MeetingDetailContract.View view) {
        super(view);
        setInteractor(new MeetingDetailInteractor());
    }

    @Override
    public void showOrganizer(AttendeesData organizerData, boolean isFirstName) {
        getView().showOrganizer(organizerData, isFirstName);
    }

    @Override
    public void showInvitieeList(List<AttendeesData> invitieesList) {
        getView().showInvitieesList(invitieesList);
    }

    @Override
    public void onDeleteMeeting() {
        getView().hideProgress();
        getView().onMeetingDeleted();
    }

    @Override
    public void setDescription(String locale, String descriptionTxt) {
        getView().setDescription(locale, descriptionTxt);
    }

    @Override
    public void showMeetingData(MeetingData meetingData) {
        getView().showMeetingData(meetingData);
    }

    @Override
    public void setGraphics(String headerColor) {
        getView().setGraphics(headerColor);
    }

    @Override
    public void onMeetingStatusUpdate(int position, String status) {
        getView().hideProgress();
        getView().onMeetingStatusUpdated(position, status);
    }

    @Override
    public void showOrganizerControls() {
        getView().showOrganizerControls();
    }

    @Override
    public void fetchData(MeetingData meetingData, Context context) {
        getInteractor().fetchData(meetingData, context, this);
    }

    @Override
    public void deleteMeeting(MeetingData meetingData, Context context) {
        getView().showProgress("Please wait...");
        getInteractor().deleteMeeting(meetingData, context, this);
    }

    @Override
    public void updateMeetingStatus(AttendeesData mAttendeeData, int attendeePosition, String status, Context context) {
        getView().showProgress("Please wait...");
        getInteractor().updateMeetingStatus(mAttendeeData, attendeePosition, status, context, this);
    }

    @Override
    public MeetingData getMeetingData() {
        return getInteractor().getMeetingData();
    }
}
