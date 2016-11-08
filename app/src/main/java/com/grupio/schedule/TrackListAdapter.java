package com.grupio.schedule;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.grupio.base.SingleElementListBaseAdapter;
import com.grupio.dao.EventDAO;
import com.grupio.data.TrackData;
import com.grupio.db.EventTable;

/**
 * Created by JSN on 25/8/16.
 */

public class TrackListAdapter extends SingleElementListBaseAdapter<TrackData> {

    boolean showTrackColor = false;
    String trackBackgroundColor = "#";

    public TrackListAdapter(Context context) {
        super(context);
        showTrackColor = EventDAO.getInstance(context).getValue(EventTable.SHOWTRACKS).equals("y");
        trackBackgroundColor = EventDAO.getInstance(context).getValue(EventTable.COLOR_THEME);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).showAllSession) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void getView(int position, SingleElementListBaseAdapter.Holder holder) {

        holder.imageView.setVisibility(View.GONE);

        if (getItemViewType(position) == 1) {
            mHolder.sessionTrack.setVisibility(View.VISIBLE);
            mHolder.sessionTrack.setBackgroundColor(Color.parseColor(trackBackgroundColor));
        } else {
            mHolder.sessionTrack.setVisibility(View.GONE);
        }

        if (showTrackColor) {
            mHolder.trackColor.setVisibility(View.VISIBLE);
            mHolder.trackColor.setBackgroundColor(Color.parseColor(!TextUtils.isEmpty(getItem(position).color) ? getItem(position).color : "#000000"));
        } else {
            mHolder.trackColor.setVisibility(View.GONE);
        }

        mHolder.name.setText(getItem(position).track);

    }
}


