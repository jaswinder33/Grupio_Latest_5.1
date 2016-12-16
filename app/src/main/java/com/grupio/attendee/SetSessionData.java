package com.grupio.attendee;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.backend.DateTime;
import com.grupio.base.BaseSetData;
import com.grupio.dao.SessionDAO;
import com.grupio.data.MeetingData;
import com.grupio.data.ScheduleData;
import com.grupio.helper.ScheduleHelper;
import com.grupio.interfaces.ClickHandler;
import com.grupio.interfaces.Person;
import com.grupio.schedule.ScheduleAdapter;
import com.grupio.schedule.ScheduleListActivity;
import com.grupio.schedule.ScheduleTrackListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 8/12/16.
 */

public class SetSessionData<T> extends BaseSetData<Person, ScheduleAdapter.ViewHolder, T> {

    private boolean showTrackColor = false;
    private String title;
    private String location;
    private String trackColor = "";
    private String track;
    private String startTime;
    private String endTime;
    private String speakerNameAsString = "";
    private String hasChild = "0";

    private boolean isFav = false;

    public SetSessionData(Context mContext) {
        super(mContext);
    }

    @Override
    public void setData(Person data, ScheduleAdapter.ViewHolder mHolder) {

        int position = getPosition(data);

        if (data instanceof ScheduleData) {
            ScheduleData mScheduleData = (ScheduleData) data;
            isFav = mScheduleData.isSessionFav();
            title = mScheduleData.getName();
            location = mScheduleData.getLocation();
            trackColor = mScheduleData.getColor();
            track = mScheduleData.getTrack();
            startTime = mScheduleData.getStart_time();
            endTime = mScheduleData.getEnd_time();
            speakerNameAsString = mScheduleData.getSpeakerNameAsString();
            hasChild = mScheduleData.getHas_child();
        } else if (data instanceof MeetingData) {
            MeetingData mMeetingData = (MeetingData) data;
            isFav = false;
            title = mMeetingData.title;
            location = "";
            trackColor = "";
            track = "";
            startTime = mMeetingData.startTime;
            endTime = mMeetingData.endTime;
            speakerNameAsString = "";
            hasChild = "0";
        }


        //Handle Star button
        if (isFav) {
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_star_on);
            mHolder.mLikeBtn.setColorFilter(Color.BLUE);
        } else {
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_star_off);
            mHolder.mLikeBtn.setColorFilter(Color.BLACK);
        }

        mHolder.sessionName.setText(title);

        //Handle Location text
        if (!TextUtils.isEmpty(location)) {
            mHolder.sessionLocation.setVisibility(View.VISIBLE);
            mHolder.sessionLocation.setText("Location: " + location);
        } else {
            mHolder.sessionLocation.setVisibility(View.GONE);
        }

        //handle track color
        if (showTrackColor) {
            try {
                mHolder.sessionTrack.setBackgroundColor(Color.parseColor(trackColor));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mHolder.sessionTrack.setBackgroundColor(Color.WHITE);
        }

        //Hanle track
        if (TextUtils.isEmpty(track)) {
            mHolder.sessionTrack.setVisibility(View.GONE);
        } else {
            mHolder.sessionTrack.setVisibility(View.VISIBLE);
            mHolder.sessionTrack.setText(track);
        }

        //Handle time
        mHolder.sessionDate.setText(ScheduleHelper.formatSessionDate(startTime, endTime));

        //Handle Speaker name
        if (TextUtils.isEmpty(speakerNameAsString)) {
            mHolder.speakerList.setVisibility(View.INVISIBLE);
        } else {
            mHolder.speakerList.setVisibility(View.VISIBLE);
            mHolder.speakerList.setText(speakerNameAsString);
        }

        //Handle + button
        if (hasChild.equals("1")) {
            mHolder.addBtn.setVisibility(View.VISIBLE);
        } else {
            mHolder.addBtn.setVisibility(View.GONE);
        }

        //Handle star button click
        mHolder.mLikeBtn.setOnClickListener(view -> {

            if (!ScheduleHelper.isLoginRequiredToLike(getContext(), ScheduleTrackListActivity.class)) {

                ClickHandler mAddScheduleToCalendar = () -> {
                    ((ScheduleData) getItem(position)).setCalenderAddId(DateTime.getInstance().saveToCalendar(getContext(), data));
                    notifyDataSetChanged();
                };

                ClickHandler mRemoveScheduleFromCalendar = () -> DateTime.getInstance().removeFromCalendar(getContext(), data);

                ClickHandler mAddSchedule = () -> {
                    ((ScheduleData) getItem(position)).setSessionFav(true);
                    ScheduleHelper.addRemoveSession("add", ((ScheduleData) getItem(position)).getSession_id(), getContext());
                    BaseActivity.CustomDialog.getDialog(getContext(), mAddScheduleToCalendar).show(getContext().getString(R.string.add_schedule_to_calendar));
                    SessionDAO.getInstance(getContext()).likeUnlikeSession(((ScheduleData) getItem(position)).getSession_id(), 1);
                    notifyDataSetChanged();
                };

                ClickHandler mRemoveSchedule = () -> {
                    ((ScheduleData) getItem(position)).setSessionFav(false);
                    ScheduleHelper.addRemoveSession("delete", ((ScheduleData) getItem(position)).getSession_id(), getContext());
                    SessionDAO.getInstance(getContext()).likeUnlikeSession(((ScheduleData) getItem(position)).getSession_id(), 0);

                    if (((ScheduleData) getItem(position)).getCalenderAddId() != null) {
                        BaseActivity.CustomDialog.getDialog(getContext(), mRemoveScheduleFromCalendar).singledBtnDialog(true).show(getContext().getString(R.string.remove_schedule_from_calendar));
                    }

                    notifyDataSetChanged();
                };

                if (((ScheduleData) getItem(position)).isSessionFav()) {
                    BaseActivity.CustomDialog.getDialog(getContext(), mRemoveSchedule).show(getContext().getString(R.string.remove_schedule));
                } else {
                    BaseActivity.CustomDialog.getDialog(getContext(), mAddSchedule).show(getContext().getString(R.string.add_schedule));
                }
            }
        });

        mHolder.addBtn.setTag(0);
        //handle + button click
        mHolder.addBtn.setOnClickListener(view -> {

            //Unregister watcher to prevent it collapse child list
            ListWatcher.getInstance().unregisterListener();

            List<ScheduleData> mScheduleDataList = new ArrayList<>();
            mScheduleDataList.addAll(SessionDAO.getInstance(getContext()).getChildSessions(((ScheduleData) getItem(position)).getSession_id()));

            if (((ScheduleData) getItem(position)).isPlus()) {

                for (int j = 0; j < getCount(); j++) {
                    for (int k = 0; k < mScheduleDataList.size(); k++) {
                        if (((ScheduleData) getItem(j)).getSession_id().equals(mScheduleDataList.get(k).getSession_id())) {
                            remove(getItem(j));
                        }
                    }
                }

                ((ScheduleData) getItem(position)).setPlus(false);
                mHolder.addBtn.setImageResource(R.drawable.ic_add);
                mHolder.addBtn.setTag(0);
            } else {
                if (!mScheduleDataList.isEmpty()) {
                    mHolder.addBtn.setImageResource(R.drawable.ic_minus_simple);
                    mHolder.addBtn.setColorFilter(Color.BLACK);
                    mHolder.addBtn.setTag(1);
                    ((ScheduleData) getItem(position)).setPlus(true);
                    addAll(mScheduleDataList);
                } else {
                    ((ScheduleData) getItem(position)).setPlus(false);
                    mHolder.addBtn.setTag(0);
                    mHolder.mChildSessionList.setVisibility(View.GONE);
                }
            }

            Fragment fragment = ((ScheduleListActivity) getContext()).getFragmentManager().findFragmentById(R.id.container);

            if (fragment != null) {
                //Register watcher again.
                ListWatcher.getInstance().registerListener((ListWatcher.Watcher) fragment);
            }

        });
    }

    public SetSessionData setShowTrackColor(boolean flag) {
        showTrackColor = flag;
        return this;
    }

}
