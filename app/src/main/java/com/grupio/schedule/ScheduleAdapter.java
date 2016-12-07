package com.grupio.schedule;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.attendee.ListWatcher;
import com.grupio.backend.DateTime;
import com.grupio.base.BaseListAdapter;
import com.grupio.dao.EventDAO;
import com.grupio.dao.SessionDAO;
import com.grupio.data.ScheduleData;
import com.grupio.db.EventTable;
import com.grupio.helper.ScheduleHelper;
import com.grupio.interfaces.ClickHandler;

import java.util.ArrayList;
import java.util.List;

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
    public int getLayout(int position) {
        return R.layout.layout_session_list_child;
    }

    @Override
    public void handleGetView(int position, ViewHolder mHolder) {

        ScheduleData mScheduleData = getItem(position);

        //Handle Star button
        if (mScheduleData.isSessionFav()) {
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_star_on);
            mHolder.mLikeBtn.setColorFilter(Color.BLUE);
        } else {
            mHolder.mLikeBtn.setImageResource(R.drawable.ic_star_off);
            mHolder.mLikeBtn.setColorFilter(Color.BLACK);
        }

        mHolder.sessionName.setText(mScheduleData.getName());

        //Handle Location text
        if (!TextUtils.isEmpty(mScheduleData.getLocation())) {
            mHolder.sessionLocation.setVisibility(View.VISIBLE);
            mHolder.sessionLocation.setText("Location: " + mScheduleData.getLocation());
        } else {
            mHolder.sessionLocation.setVisibility(View.GONE);
        }

        //handle track color
        if (showTrackColor) {
            try {
                mHolder.sessionTrack.setBackgroundColor(Color.parseColor(getItem(position).getColor()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mHolder.sessionTrack.setBackgroundColor(Color.WHITE);
        }

        //Hanle track
        if (TextUtils.isEmpty(getItem(position).getTrack())) {
            mHolder.sessionTrack.setVisibility(View.GONE);
        } else {
            mHolder.sessionTrack.setVisibility(View.VISIBLE);
            mHolder.sessionTrack.setText(mScheduleData.getTrack());
        }

        //Handle time
        mHolder.sessionDate.setText(ScheduleHelper.formatSessionDate(mScheduleData.getStart_time(), mScheduleData.getEnd_time()));

        //Handle Speaker name
        if (TextUtils.isEmpty(mScheduleData.getSpeakerNameAsString())) {
            mHolder.speakerList.setVisibility(View.INVISIBLE);
        } else {
            mHolder.speakerList.setVisibility(View.VISIBLE);
            mHolder.speakerList.setText(mScheduleData.getSpeakerNameAsString());
        }

        //Handle + button
        if (mScheduleData.getHas_child().equals("1")) {
            mHolder.addBtn.setVisibility(View.VISIBLE);
        } else {
            mHolder.addBtn.setVisibility(View.GONE);
        }

        //Handle star button click
        mHolder.mLikeBtn.setOnClickListener(view -> {

            if (!ScheduleHelper.isLoginRequiredToLike(getContext(), ScheduleTrackListActivity.class)) {

                ClickHandler mAddScheduleToCalendar = () -> {

                    getItem(position).setCalenderAddId(DateTime.getInstance().saveToCalendar(getContext(), mScheduleData));
                };

                ClickHandler mRemoveScheduleFromCalendar = () -> DateTime.getInstance().removeFromCalendar(getContext(), mScheduleData);

                ClickHandler mAddSchedule = () -> {
                    getItem(position).setSessionFav(true);
                    ScheduleHelper.addRemoveSession("add", mScheduleData.getSession_id(), getContext());
                    BaseActivity.CustomDialog.getDialog(getContext(), mAddScheduleToCalendar).show(getContext().getString(R.string.add_schedule_to_calendar));
                    SessionDAO.getInstance(getContext()).likeUnlikeSession(mScheduleData.getSession_id(), 1);
                    notifyDataSetChanged();
                };

                ClickHandler mRemoveSchedule = () -> {
                    getItem(position).setSessionFav(false);
                    ScheduleHelper.addRemoveSession("delete", mScheduleData.getSession_id(), getContext());
                    SessionDAO.getInstance(getContext()).likeUnlikeSession(mScheduleData.getSession_id(), 0);

                    if (getItem(position).getCalenderAddId() != null) {
                        BaseActivity.CustomDialog.getDialog(getContext(), mRemoveScheduleFromCalendar).singledBtnDialog(true).show(getContext().getString(R.string.remove_schedule_from_calendar));
                    }

                    notifyDataSetChanged();
                };

                if (mScheduleData.isSessionFav()) {
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

            int i = (int) mHolder.addBtn.getTag();

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


    @Override
    public ViewHolder setViewHolder(View convertView, int position) {
        return new ViewHolder(convertView);
    }

    class ViewHolder {

        ImageButton mLikeBtn, addBtn;
        TextView sessionName, speakerList, sessionDate, sessionLocation, sessionTrack;
        ListView mChildSessionList;

        ViewHolder(View view) {
            mLikeBtn = (ImageButton) view.findViewById(R.id.favBtn);
            addBtn = (ImageButton) view.findViewById(R.id.addBtn);
            sessionName = (TextView) view.findViewById(R.id.sessionName);
            speakerList = (TextView) view.findViewById(R.id.sessionSpeakerList);
            sessionDate = (TextView) view.findViewById(R.id.sessionDate);
            sessionLocation = (TextView) view.findViewById(R.id.sessionLocation);
            sessionTrack = (TextView) view.findViewById(R.id.sessionTrack);
            mChildSessionList = (ListView) view.findViewById(R.id.childList);
        }
    }

}
