package com.grupio.venuemaps;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.SimpleBaseListAdapter;
import com.grupio.data.AlertData;

/**
 * Created by JSN on 2/11/16.
 */

public class AlertAdapter extends SimpleBaseListAdapter<AlertData, AlertAdapter.ViewHolder> {

    public AlertAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.layout_alert_list_child;
    }

    @Override
    public ViewHolder setViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public void getView(int position, ViewHolder viewHolder) {
        viewHolder.dateTxt.setText(getItem(position).getDate());
        viewHolder.notificationTxt.setText(getItem(position).getNotificationText());
        if (getItem(position).isRead()) {
            viewHolder.mark.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mark.setVisibility(View.VISIBLE);
        }
    }

    public class ViewHolder {

        private ImageView mark;
        private TextView dateTxt, notificationTxt;

        public ViewHolder(View view) {
            mark = (ImageView) view.findViewById(R.id.mark);
            dateTxt = (TextView) view.findViewById(R.id.dateTime);
            notificationTxt = (TextView) view.findViewById(R.id.notificationTxt);
        }
    }


}
