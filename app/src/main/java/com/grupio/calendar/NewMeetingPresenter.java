package com.grupio.calendar;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.base.BasePresenter;
import com.grupio.data.MeetingData;

/**
 * Created by JSN on 19/12/16.
 */

public class NewMeetingPresenter extends BasePresenter<NewMeetingContract.View, NewMeetingContract.Interactor> implements NewMeetingContract.Presenter, NewMeetingContract.OnInteraction {

    public NewMeetingPresenter(NewMeetingContract.View view) {
        super(view);
        setInteractor(new NewMeetingInteractor());
    }

    @Override
    public void onDataValidated(MeetingData data) {
        getView().showData(data);
    }

    @Override
    public void onMeetingCreated() {

    }

    @Override
    public void onTimeZone(String timezone) {
        getView().showTimeZone(timezone);
    }

    @Override
    public void showData(Context context, MeetingData data) {
        getInteractor().showData(context, data, this);
    }

    @Override
    public void validateGoToNext(Context context, MeetingData data) {
        if (TextUtils.isEmpty(data.title) || TextUtils.isEmpty(data.location) || TextUtils.isEmpty(data.description) || data.meetingDate.equals(context.getString(R.string.date))) {
            getView().showErrorMessage(context.getString(R.string.form_incomplete));
        } else {

            MeetingData olderData = getInteractor().getMeetingData(context);

            if (olderData != null) {
                olderData.title = data.title;
                olderData.location = data.location;
                olderData.description = data.description;
                olderData.meetingDate = data.meetingDate;
            }

            getView().goToNextScreen(olderData);
        }
    }

    @Override
    public void createMeeting(Context context, MeetingData data) {
    }

    @Override
    public void fetchTimeZone(Context context) {
        getInteractor().fetchTimeZone(context, this);
    }

    @Override
    public MeetingData getMeetingData(Context context) {
        return getInteractor().getMeetingData(context);
    }

}