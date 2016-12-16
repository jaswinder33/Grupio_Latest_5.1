package com.grupio.calendar;

import android.content.Context;

import com.grupio.attendee.SetSessionData;
import com.grupio.data.MeetingData;
import com.grupio.helper.ScheduleHelper;
import com.grupio.interfaces.Person;
import com.grupio.schedule.ScheduleAdapter;

/**
 * Created by JSN on 15/12/16.
 */

public class SetCalendarData<T> extends SetSessionData<T> {

    public SetCalendarData(Context mContext) {
        super(mContext);
    }

    @Override
    public void setData(Person data, ScheduleAdapter.ViewHolder mHolder) {
        super.setData(data, mHolder);

        if (data instanceof MeetingData) {
            MeetingData mMeetingData = (MeetingData) data;

            //Handle time
            mHolder.sessionDate.setText(ScheduleHelper.formatSessionDate(mMeetingData.startTime, mMeetingData.endTime, "HH:mm:ss"));

        }

        mHolder.mLikeBtn.setOnClickListener(null);
        mHolder.addBtn.setOnClickListener(null);
    }

}
