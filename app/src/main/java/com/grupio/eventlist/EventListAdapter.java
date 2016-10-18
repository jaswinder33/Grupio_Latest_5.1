package com.grupio.eventlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.data.EventData;
import com.grupio.session.ConstantData;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 5/9/16.
 */
public class EventListAdapter extends ArrayAdapter<EventData> {

    Holder mHolder;

    public EventListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_eventlist, parent, false);
            mHolder = new Holder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }

        mHolder.txtEventName.setText(getItem(position).getEvent_name());
        try {
            String url = ConstantData.BASE_URL  +getItem(position).getImageURL();
            ImageLoader.getInstance().displayImage(url, mHolder.img, Utility.getDisplayOptionsEventList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getItem(position).getEnd_date() != null && getItem(position).getCity() != null)
            mHolder.txtEventDate.setText(getItem(position).getStart_date() + " - " + getItem(position).getEnd_date() + ", " + getItem(position).getCity());
        else
            mHolder.txtEventDate.setText(getItem(position).getStart_date());

        return convertView;
    }

    class Holder {
        private ImageView img;
        private TextView txtEventName, txtEventDate;

        Holder(View view) {
            img = (ImageView) view.findViewById(R.id.eventImage);
            txtEventDate = (TextView) view.findViewById(R.id.txtDate);
            txtEventName = (TextView) view.findViewById(R.id.txtEventName);

        }
    }
}
