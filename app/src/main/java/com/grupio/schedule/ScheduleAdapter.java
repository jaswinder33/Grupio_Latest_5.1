package com.grupio.schedule;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.base.BaseListAdapter;
import com.grupio.dao.EventDAO;
import com.grupio.data.ScheduleData;
import com.grupio.db.EventTable;
import com.grupio.helper.ScheduleHelper;
import com.grupio.interfaces.ClickHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by JSN on 7/11/16.
 */

public class ScheduleAdapter extends BaseListAdapter<ScheduleData, ScheduleAdapter.ViewHolder> {

    boolean showTrackColor = false;

    public ScheduleAdapter(Context context) {
        super(context);
        showTrackColor = EventDAO.getInstance(context).getValue(EventTable.SHOWTRACKS).equals("y");

    }

    @Override
    public String getFirstName(int position) {
        return getItem(position).getName();
    }

    @Override
    public String getLastName(int position) {
        return getItem(position).getName();
    }

    @Override
    public int getLayout() {
        return R.layout.layout_session_list_child;
    }

    @Override
    public void handleGetView(int position, ViewHolder mHolder) {

        ScheduleData mScheduleData = getItem(position);

        if (mScheduleData.isSessionFav()) {
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_star_on);
            mHolder.mLikeBtn.setColorFilter(Color.BLUE);
        } else {
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_star_off);
            mHolder.mLikeBtn.setColorFilter(Color.BLACK);
        }

        mHolder.sessionName.setText(mScheduleData.getName());

        if (!TextUtils.isEmpty(mScheduleData.getLocation())) {
            mHolder.sessionLocation.setVisibility(View.VISIBLE);
            mHolder.sessionLocation.setText("Location: " + mScheduleData.getLocation());
        } else {
            mHolder.sessionLocation.setVisibility(View.GONE);
        }

        if (showTrackColor) {
            mHolder.sessionTrack.setBackgroundColor(Color.parseColor(getItem(position).getColor()));
        } else {
            mHolder.sessionTrack.setBackgroundColor(Color.WHITE);
        }

        if (TextUtils.isEmpty(getItem(position).getTrack())) {
            mHolder.sessionTrack.setVisibility(View.GONE);
        } else {
            mHolder.sessionTrack.setVisibility(View.VISIBLE);
        }

        //set session time
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calStart = Calendar.getInstance();
        Calendar calend = Calendar.getInstance();

        try {
            calStart.setTime(sdf1.parse(mScheduleData.getStart_time()));
            calend.setTime(sdf1.parse(mScheduleData.getEnd_time()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long time = calStart.getTimeInMillis();
        long timeend = calend.getTimeInMillis();

        mHolder.sessionDate.setText(mScheduleData.getStartHour("" + time) + " - " + mScheduleData.getEndHour("" + timeend));

        if (TextUtils.isEmpty(mScheduleData.getSpeakerNameAsString())) {
            mHolder.speakerList.setVisibility(View.INVISIBLE);
        } else {
            mHolder.speakerList.setVisibility(View.VISIBLE);
            mHolder.speakerList.setText(mScheduleData.getSpeakerNameAsString());
        }

        mHolder.mLikeBtn.setOnClickListener(view -> {

            if (!ScheduleHelper.isLoginRequiredToLike(getContext(), ScheduleTrackListActivity.class)) {

                ClickHandler mAddScheduleToCalendar = () -> ScheduleHelper.saveToCalendar(getContext(), mScheduleData);

                ClickHandler mRemoveScheduleFromCalendar = () -> ScheduleHelper.removeFromCalendar(getContext(), mScheduleData);

                ClickHandler mAddSchedule = () -> {
                    getItem(position).setSessionFav(true);
                    ScheduleHelper.addRemoveSession("add", mScheduleData.getSession_id(), getContext());
                    BaseActivity.CustomDialog.getDialog(getContext(), mAddScheduleToCalendar).show(getContext().getString(R.string.add_schedule_to_calendar));
                };

                ClickHandler mRemoveSchedule = () -> {
                    getItem(position).setSessionFav(false);
                    ScheduleHelper.addRemoveSession("delete", mScheduleData.getSession_id(), getContext());
                    BaseActivity.CustomDialog.getDialog(getContext(), mRemoveScheduleFromCalendar).singledBtnDialog(true).show(getContext().getString(R.string.remove_schedule));
                };


                if (mScheduleData.isSessionFav()) {
                    BaseActivity.CustomDialog.getDialog(getContext(), mRemoveSchedule).show(getContext().getString(R.string.remove_schedule));
                } else {
                    BaseActivity.CustomDialog.getDialog(getContext(), mAddSchedule).show(getContext().getString(R.string.add_schedule));
                }

                notifyDataSetChanged();
            }

        });

    }


    @Override
    public ViewHolder setViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    class ViewHolder {

        ImageButton mLikeBtn, addBtn;
        TextView sessionName, speakerList, sessionDate, sessionLocation, sessionTrack;

        ViewHolder(View view) {
            mLikeBtn = (ImageButton) view.findViewById(R.id.favBtn);
            addBtn = (ImageButton) view.findViewById(R.id.addBtn);
            sessionName = (TextView) view.findViewById(R.id.sessionName);
            speakerList = (TextView) view.findViewById(R.id.sessionSpeakerList);
            sessionDate = (TextView) view.findViewById(R.id.sessionDate);
            sessionLocation = (TextView) view.findViewById(R.id.sessionLocation);
            sessionTrack = (TextView) view.findViewById(R.id.sessionTrack);
        }
    }

}
