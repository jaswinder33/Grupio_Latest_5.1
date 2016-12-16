package com.grupio.calendar;

import android.content.Context;

import com.grupio.data.MeetingData;
import com.grupio.schedule.ScheduleAdapter;

/**
 * Created by JSN on 15/12/16.
 */

public class CalendarListAdapter extends ScheduleAdapter {

    public CalendarListAdapter(Context context) {
        super(context);
    }

    @Override
    public String getFirstName(int position) {
        super.getFirstName(position);

        if (getItem(position) instanceof MeetingData) {
            return ((MeetingData) getItem(position)).title;
        }

        return "";
    }

    @Override
    public void handleGetView(int position, ViewHolder mHolder) {
        SetCalendarData<CalendarListAdapter> mSetCalendarData = new SetCalendarData(getContext());
        mSetCalendarData.setAdapter(this);
        mSetCalendarData.setShowTrackColor(showTrackColor)
                .setData(getItem(position), mHolder);

    }
}
