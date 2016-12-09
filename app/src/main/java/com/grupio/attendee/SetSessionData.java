package com.grupio.attendee;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.backend.DateTime;
import com.grupio.base.BaseSetData;
import com.grupio.dao.SessionDAO;
import com.grupio.data.ScheduleData;
import com.grupio.helper.ScheduleHelper;
import com.grupio.interfaces.ClickHandler;
import com.grupio.schedule.ScheduleAdapter;
import com.grupio.schedule.ScheduleListActivity;
import com.grupio.schedule.ScheduleTrackListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mani on 8/12/16.
 */

public class SetSessionData<T extends ArrayAdapter> extends BaseSetData<ScheduleData, ScheduleAdapter.ViewHolder, T> {

    boolean showTrackColor = false;

    public SetSessionData(Context mContext) {
        super(mContext);
    }

    @Override
    public void setData(ScheduleData data, ScheduleAdapter.ViewHolder mHolder) {

        int position = getPosition(data);

        //Handle Star button
        if (data.isSessionFav()) {
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_star_on);
            mHolder.mLikeBtn.setColorFilter(Color.BLUE);
        } else {
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_star_off);
            mHolder.mLikeBtn.setColorFilter(Color.BLACK);
        }

        mHolder.sessionName.setText(data.getName());

        //Handle Location text
        if (!TextUtils.isEmpty(data.getLocation())) {
            mHolder.sessionLocation.setVisibility(View.VISIBLE);
            mHolder.sessionLocation.setText("Location: " + data.getLocation());
        } else {
            mHolder.sessionLocation.setVisibility(View.GONE);
        }

        //handle track color
        if (showTrackColor) {
            try {
                mHolder.sessionTrack.setBackgroundColor(Color.parseColor(data.getColor()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mHolder.sessionTrack.setBackgroundColor(Color.WHITE);
        }

        //Hanle track
        if (TextUtils.isEmpty(data.getTrack())) {
            mHolder.sessionTrack.setVisibility(View.GONE);
        } else {
            mHolder.sessionTrack.setVisibility(View.VISIBLE);
            mHolder.sessionTrack.setText(data.getTrack());
        }

        //Handle time
        mHolder.sessionDate.setText(ScheduleHelper.formatSessionDate(data.getStart_time(), data.getEnd_time()));

        //Handle Speaker name
        if (TextUtils.isEmpty(data.getSpeakerNameAsString())) {
            mHolder.speakerList.setVisibility(View.INVISIBLE);
        } else {
            mHolder.speakerList.setVisibility(View.VISIBLE);
            mHolder.speakerList.setText(data.getSpeakerNameAsString());
        }

        //Handle + button
        if (data.getHas_child().equals("1")) {
            mHolder.addBtn.setVisibility(View.VISIBLE);
        } else {
            mHolder.addBtn.setVisibility(View.GONE);
        }

        //Handle star button click
        mHolder.mLikeBtn.setOnClickListener(view -> {

            if (!ScheduleHelper.isLoginRequiredToLike(getContext(), ScheduleTrackListActivity.class)) {

                ClickHandler mAddScheduleToCalendar = () -> {
                    getItem(position).setCalenderAddId(DateTime.getInstance().saveToCalendar(getContext(), data));
                };

                ClickHandler mRemoveScheduleFromCalendar = () -> DateTime.getInstance().removeFromCalendar(getContext(), data);

                ClickHandler mAddSchedule = () -> {
                    getItem(position).setSessionFav(true);
                    ScheduleHelper.addRemoveSession("add", data.getSession_id(), getContext());
                    BaseActivity.CustomDialog.getDialog(getContext(), mAddScheduleToCalendar).show(getContext().getString(R.string.add_schedule_to_calendar));
                    SessionDAO.getInstance(getContext()).likeUnlikeSession(data.getSession_id(), 1);
                    notifyDataSetChanged();
                };

                ClickHandler mRemoveSchedule = () -> {
                    getItem(position).setSessionFav(false);
                    ScheduleHelper.addRemoveSession("delete", data.getSession_id(), getContext());
                    SessionDAO.getInstance(getContext()).likeUnlikeSession(data.getSession_id(), 0);

                    if (getItem(position).getCalenderAddId() != null) {
                        BaseActivity.CustomDialog.getDialog(getContext(), mRemoveScheduleFromCalendar).singledBtnDialog(true).show(getContext().getString(R.string.remove_schedule_from_calendar));
                    }

                    notifyDataSetChanged();
                };

                if (data.isSessionFav()) {
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
            mScheduleDataList.addAll(SessionDAO.getInstance(getContext()).getChildSessions(getItem(position).getSession_id()));

            if (getItem(position).isPlus()) {

                for (int j = 0; j < getCount(); j++) {
                    for (int k = 0; k < mScheduleDataList.size(); k++) {
                        if (getItem(j).getSession_id().equals(mScheduleDataList.get(k).getSession_id())) {
                            remove(getItem(j));
                        }
                    }
                }

                getItem(position).setPlus(false);
                mHolder.addBtn.setImageResource(R.drawable.ic_add);
                mHolder.addBtn.setTag(0);
            } else {
                if (!mScheduleDataList.isEmpty()) {
                    mHolder.addBtn.setImageResource(R.drawable.ic_minus_simple);
                    mHolder.addBtn.setColorFilter(Color.BLACK);
                    mHolder.addBtn.setTag(1);
                    getItem(position).setPlus(true);
                    addAll(mScheduleDataList);
                } else {
                    getItem(position).setPlus(false);
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

//    public SetSessionData setAdapter(T mObj) {
//        mAdapter = mObj;
//        return this;
//    }

    public SetSessionData setShowTrackColor(boolean flag) {
        showTrackColor = flag;
        return this;
    }

  /*  public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }*/
}
