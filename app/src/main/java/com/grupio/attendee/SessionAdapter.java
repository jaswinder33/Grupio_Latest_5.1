package com.grupio.attendee;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.BaseListAdapter;
import com.grupio.dao.EventDAO;
import com.grupio.data.ScheduleData;
import com.grupio.db.EventTable;

/**
 * Created by JSN on 20/10/16.
 */

public class SessionAdapter extends BaseListAdapter<ScheduleData, SessionAdapter.ViewHolder> {

    boolean showTrackColor = false;
    String trackColor = "#";

    public SessionAdapter(Context context) {
        super(context);
        showTrackColor = EventDAO.getInstance(context).getValue(EventTable.SHOWTRACKS).equals("y");
        trackColor = EventDAO.getInstance(context).getValue(EventTable.COLOR_THEME);
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
        return R.layout.layout_session;
    }

    @Override
    public void handleGetView(int position, View view, ViewHolder mHolder) {

        if (showTrackColor) {
            mHolder.trackColor.setVisibility(View.VISIBLE);
            mHolder.trackColor.setBackgroundColor(Color.parseColor(!TextUtils.isEmpty(getItem(position).getColor()) ? getItem(position).getColor() : "#000000"));
        } else {
            mHolder.trackColor.setVisibility(View.GONE);
        }

        mHolder.sessionName.setText(getItem(position).getName());
    }

    @Override
    public ViewHolder setViewHolder(View convertView, int position) {
        return new ViewHolder(convertView);
    }

    public class ViewHolder {
        TextView sessionName;
        View trackColor;
        TextView sessionTrack;

        public ViewHolder(View view) {
            sessionName = (TextView) view.findViewById(R.id.sessionName);
            trackColor = view.findViewById(R.id.trackColor);
            sessionTrack = (TextView) view.findViewById(R.id.session_track);
        }

    }

}
