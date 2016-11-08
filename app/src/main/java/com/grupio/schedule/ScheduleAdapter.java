package com.grupio.schedule;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.BaseListAdapter;
import com.grupio.dao.EventDAO;
import com.grupio.data.ScheduleData;
import com.grupio.db.EventTable;

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
        mHolder.sessionDate.setText(mScheduleData.getStart_time());

        if (!TextUtils.isEmpty(mScheduleData.getLocation())) {
            mHolder.sessionLocation.setText(mScheduleData.getLocation());
        } else {
            mHolder.sessionLocation.setVisibility(View.GONE);
        }

        if (showTrackColor) {
//            mHolder.sessionTrack.setBackgroundColor();
        }


//        mHolder.sessionTrack.setText();
//        mHolder.speakerList.setText();

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
