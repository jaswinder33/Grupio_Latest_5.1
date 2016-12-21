package com.grupio.calendar;

import android.content.Context;

import com.grupio.R;
import com.grupio.apis.MeetingDeleteAPI;
import com.grupio.apis.MeetingResponseAPI;
import com.grupio.attendee.ListWatcher;
import com.grupio.backend.DateTime;
import com.grupio.base.BaseInteractor;
import com.grupio.dao.AttendeeDAO;
import com.grupio.dao.EventDAO;
import com.grupio.dao.MeetingDAO;
import com.grupio.data.AttendeesData;
import com.grupio.data.MeetingData;
import com.grupio.db.EventTable;
import com.grupio.helper.MeetingHelper;
import com.grupio.message.apis.APICallBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JSN on 20/12/16.
 */

public class MeetingDetailInteractor extends BaseInteractor implements MeetingDetailContract.Interactor {

    private MeetingData mMeetingData;

    @Override
    public void fetchData(MeetingData meetingData, Context context, MeetingDetailContract.OnInteraction listener) {

        String meetingDate = meetingData.meetingDate;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfnew = new SimpleDateFormat("MMM dd, yyyy");

        try {
            Date date = sdf.parse(meetingDate);
            meetingData.meetingDate = sdfnew.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MeetingHelper mHelper = new MeetingHelper();

        String[] time = mHelper.parseMeetingTime(meetingData.meetingTime);

        meetingData.startTime = DateTime.getInstance().getTime(time[0]);
        meetingData.endTime = DateTime.getInstance().getTime(time[1]);
        meetingData.invitiesList.addAll(mHelper.parseInvitations(meetingData.invities, context));
        meetingData.mCreatorData = new AttendeesData(AttendeeDAO.getInstance(context).getAttendeeDetal(meetingData.creator));

        listener.setGraphics(EventDAO.getInstance(context).getValue(EventTable.COLOR_THEME));
        listener.showMeetingData(meetingData);
        listener.setDescription(context.getString(R.string.description), meetingData.description);
        listener.showOrganizer(meetingData.mCreatorData, EventDAO.getInstance(context).getValue(EventTable.NAME_ORDER).equals("firstname_lastname"));
        listener.showInvitieeList(meetingData.invitiesList);

        mMeetingData = new MeetingData(meetingData);
    }

    @Override
    public void deleteMeeting(MeetingData meetingData, Context context, MeetingDetailContract.OnInteraction listener) {
        new MeetingDeleteAPI(context, new APICallBack() {
            @Override
            public void onSuccess() {

                ListWatcher.getInstance().notifyList();

                MeetingDAO.getInstance(context).deleteMeeting(meetingData.id);
                ListWatcher.getInstance().notifyList();
                listener.onDeleteMeeting();
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        }).doCall(meetingData);
    }

    @Override
    public void updateMeetingStatus(AttendeesData mAttendeeData, int attendeePosition, String status, Context context, MeetingDetailContract.OnInteraction listener) {
        Object[] requestParams = new Object[4];
        requestParams[0] = mMeetingData;

        requestParams[1] = status;

        new MeetingResponseAPI(context, new APICallBack() {
            @Override
            public void onSuccess() {

                MeetingData mData = MeetingDAO.getInstance(context).getMeeting(mMeetingData.id);

                MeetingHelper mHelper = new MeetingHelper();

                if (mData != null) {
                    mData.invities = mHelper.updateInvitiesStatus(attendeePosition, status, mData.invities);
                }

                MeetingDAO.getInstance(context).updateMeeting(mData);

                listener.onMeetingStatusUpdate(attendeePosition, status);

                ListWatcher.getInstance().notifyList();
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        }).doCall(requestParams);

    }


    @Override
    public MeetingData getMeetingData() {
        return mMeetingData;
    }
}
