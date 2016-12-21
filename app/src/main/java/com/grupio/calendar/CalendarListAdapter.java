package com.grupio.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.grupio.R;
import com.grupio.base.BaseListAdapter;
import com.grupio.dao.EventDAO;
import com.grupio.data.AttendeesData;
import com.grupio.data.MeetingData;
import com.grupio.data.ScheduleData;
import com.grupio.db.EventTable;
import com.grupio.interfaces.Person;
import com.grupio.schedule.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 15/12/16.
 */

public class CalendarListAdapter extends BaseListAdapter<Person, ScheduleAdapter.ViewHolder> {

    private boolean showTrackColor = false;

    private String eventColor;

    public CalendarListAdapter(Context context) {
        super(context);
        showTrackColor = EventDAO.getInstance(context).getValue(EventTable.SHOWTRACKS).equals("y");
        eventColor = EventDAO.getInstance(context).getValue(EventTable.COLOR_THEME);
    }


    @Override
    public String getFirstName(int position) {

        if (getItem(position) instanceof MeetingData) {
            return ((MeetingData) getItem(position)).title;
        } else if (getItem(position) instanceof ScheduleData) {
            return ((ScheduleData) getItem(position)).getName();
        }

        return "";
    }

    @Override
    public String getLastName(int position) {
        return getFirstName(position);
    }

    @Override
    public int getLayout(int position) {
        return R.layout.layout_session_list_child;
    }

    @Override
    public void handleGetView(int position, View view, ScheduleAdapter.ViewHolder mHolder) {

        SetCalendarData<CalendarListAdapter> mSetCalendarData = new SetCalendarData(getContext());
        mSetCalendarData.setAdapter(this);
        mSetCalendarData.setShowTrackColor(showTrackColor)
                .setData(getItem(position), mHolder);


        if (getItem(position) instanceof ScheduleData) {
            mHolder.mLikeBtn.setImageResource(R.drawable.meeting_icon);
            view.setBackgroundColor(getContext().getResources().getColor(R.color.calendar_grey));
        } else {

            boolean isAccepted = false;

            List<AttendeesData> invitiesList = new ArrayList<>();
            invitiesList.addAll(((MeetingData) getItem(position)).invitiesList);

            for (int i = 0; i < invitiesList.size(); i++) {
                if (invitiesList.get(i).getMeetingStatus().equals("2")) {
                    isAccepted = true;
                    break;
                }
            }

            if (isAccepted) {
                view.setBackgroundColor(Color.parseColor(eventColor));
            } else {
                view.setBackgroundColor(getContext().getResources().getColor(R.color.calendar_grey));
            }
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_clock);
        }

        mHolder.mLikeBtn.setColorFilter(Color.WHITE);
        mHolder.mLikeBtn.setOnClickListener(null);
        mHolder.addBtn.setOnClickListener(null);

    }

    @Override
    public ScheduleAdapter.ViewHolder setViewHolder(View convertView, int position) {
        return new ScheduleAdapter.ViewHolder(convertView);
    }


}
