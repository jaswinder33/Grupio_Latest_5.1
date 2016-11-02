package com.grupio.attendee;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
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

    public SessionAdapter(Context context) {
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
        return R.layout.layout_session;
    }

    @Override
    public void handleGetView(int position, ViewHolder mHolder) {

        Log.i("Session Adapter", "handleGetView: " + getItem(position).getName());
        if (showTrackColor) {
            mHolder.trackColor.setVisibility(View.VISIBLE);
            mHolder.trackColor.setBackgroundColor(Color.parseColor(!TextUtils.isEmpty(getItem(position).getColor()) ? getItem(position).getColor() : "#000000"));
        } else {
            mHolder.trackColor.setVisibility(View.GONE);
        }

        mHolder.sessionName.setText(getItem(position).getName());
    }

    @Override
    public ViewHolder setViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    public class ViewHolder {
        View trackColor;
        TextView sessionName;

        public ViewHolder(View view) {
            trackColor = view.findViewById(R.id.trackColor);
            sessionName = (TextView) view.findViewById(R.id.sessionName);
        }

    }

}
