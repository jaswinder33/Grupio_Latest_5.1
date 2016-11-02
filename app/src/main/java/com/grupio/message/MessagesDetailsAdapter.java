package com.grupio.message;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.dao.AttendeeDAO;
import com.grupio.dao.EventDAO;
import com.grupio.data.MessageData;
import com.grupio.db.AttendeeTable;
import com.grupio.db.EventTable;
import com.grupio.session.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MessagesDetailsAdapter extends ArrayAdapter<MessageData> {

    Context mcontext;
    Holder mHolder;
    private boolean isInbox = false;

    public MessagesDetailsAdapter(Context mcontext, boolean isInbox) {
        super(mcontext, 0);
        this.mcontext = mcontext;
        this.isInbox = isInbox;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.message_details_adapter_page, null);
            mHolder = new Holder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }
        mHolder.attendee_name.setText(getItem(position).getSender_first_name() + " " + getItem(position).getSender_last_name());

        String attendeeId = Preferences.getInstances(mcontext).getAttendeeId();

        String id = "";

        if (isInbox) {
            if (!getItem(position).getReceiver_id().equals(attendeeId)) {
                id = getItem(position).getReceiver_id();
                mHolder.rl.setBackgroundColor(Color.WHITE);
            } else {
                mHolder.rl.setBackgroundColor(Color.parseColor("#aacccccc"));
            }
        } else {
            id = getItem(position).getSender_id();
            if (getItem(position).getSender_id().equals(attendeeId)) {
                mHolder.rl.setBackgroundColor(Color.WHITE);
            } else {
                mHolder.rl.setBackgroundColor(Color.parseColor("#aacccccc"));
            }
        }

        String showAttendeeImage = EventDAO.getInstance(getContext()).getValue(EventTable.HIDE_ATTENDEE_IMAGES);

        if (showAttendeeImage.equalsIgnoreCase("n")) {
            String imageUrl = getContext().getString(R.string.base_url) + AttendeeDAO.getInstance(getContext()).getValue(AttendeeTable.IMAGE, id);
            ImageLoader.getInstance().displayImage(imageUrl, mHolder.attendee_image, Utility.getDisplayOptionsAttendee());
        }


        mHolder.attendee_content.setText(getItem(position).getContent());
        mHolder.attendee_title.setText(getItem(position).getTitle());
        mHolder.message_date.setText(getItem(position).gettime());
        return convertView;
    }

    private class Holder {
        TextView attendee_name, attendee_content, attendee_title, message_date;
        ImageView attendee_image;
        View rl;

        Holder(View view) {
            attendee_name = (TextView) view.findViewById(R.id.attendee_name);
//            attendee_name.setTypeface(ConstantData.typeface, Typeface.NORMAL);
            attendee_image = (ImageView) view.findViewById(R.id.attendee_image);
            rl = view.findViewById(R.id.relative_layout);
            attendee_content = (TextView) view.findViewById(R.id.attendee_content);
            attendee_title = (TextView) view.findViewById(R.id.attendee_title);
            message_date = (TextView) view.findViewById(R.id.message_date);
        }
    }

}