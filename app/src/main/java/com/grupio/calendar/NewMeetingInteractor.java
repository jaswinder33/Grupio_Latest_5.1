package com.grupio.calendar;

import android.content.Context;

import com.grupio.base.BaseInteractor;
import com.grupio.dao.EventDAO;
import com.grupio.data.MeetingData;
import com.grupio.db.EventTable;

/**
 * Created by JSN on 19/12/16.
 */

public class NewMeetingInteractor extends BaseInteractor implements NewMeetingContract.Interactor {

    @Override
    public void createMeeting(Context context, MeetingData data, NewMeetingContract.OnInteraction listener) {
    }

    @Override
    public void fetchTimeZone(Context context, NewMeetingContract.OnInteraction listener) {
        listener.onTimeZone(EventDAO.getInstance(context).getValue(EventTable.TIMEZONE));
    }
}
