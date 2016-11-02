package com.grupio.venuemaps;

import android.content.Context;

import com.grupio.base.SingleElementListBaseAdapter;
import com.grupio.data.MapsData;

/**
 * Created by JSN on 24/10/16.
 */

public class MapsListAdapter extends SingleElementListBaseAdapter<MapsData> {
    public MapsListAdapter(Context context) {
        super(context);
    }

    @Override
    public void getView(int position, SingleElementListBaseAdapter.Holder holder) {
        holder.name.setText(getItem(position).getName());
    }
}

