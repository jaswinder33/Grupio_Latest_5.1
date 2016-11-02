package com.grupio.message;

import android.content.Context;
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
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 7/7/16.
 */
public class MessageAdapter extends ArrayAdapter<MessageData> {

    private Holder mHolder;
    private boolean isInbox = false;
    private String showAttendeeImage;

    public MessageAdapter(Context mContext, boolean isInbox) {
        super(mContext, 0);
        this.isInbox = isInbox;
        showAttendeeImage = EventDAO.getInstance(getContext()).getValue(EventTable.HIDE_ATTENDEE_IMAGES);
    }

    public void setIsInbox(boolean isinbox) {
        this.isInbox = isinbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_adapter_layout, parent, false);
            mHolder = new Holder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }

        MessageData mData = getItem(position);
        String attId;
        if (isInbox) {
            try {
                mHolder.attendee_name.setText(mData.getSender_first_name() + " " + mData.getSender_last_name());
            } catch (Exception e) {
            }
            attId = mData.getSender_id();
        } else {
            try {
                mHolder.attendee_name.setText(mData.getReceiver_first_name() + " " + mData.getReceiver_last_name());
            } catch (Exception e) {
            }
            attId = mData.getReceiver_id();
        }

        if (showAttendeeImage.equalsIgnoreCase("n")) {
            String imageUrl = getContext().getString(R.string.base_url) + AttendeeDAO.getInstance(getContext()).getValue(AttendeeTable.IMAGE, attId);
            ImageLoader.getInstance().displayImage(imageUrl, mHolder.attendeeImage, Utility.getDisplayOptionsAttendee());
        }

        mHolder.attendee_title.setText(mData.getTitle());
        mHolder.message_date.setText(mData.gettime());
        mHolder.attendee_msg.setText(mData.getContent());

        // to show the read & unread image..acc. to 1- unread , 0 -read..

        if (isInbox && mData.getIs_unread().equals("1")) {
            // for unread message...
            mHolder.msgImg_view.setBackgroundResource(R.drawable.unread_message);
            mHolder.msgImg_view.setVisibility(View.VISIBLE);
        } else if (isInbox && mData.getIs_unread().equals("0")) {
            // for read message...
            mHolder.msgImg_view.setBackgroundResource(R.drawable.read_message);
            mHolder.msgImg_view.setVisibility(View.VISIBLE);
        } else {
            // gone for other case or send messages..
            mHolder.msgImg_view.setVisibility(View.GONE);
        }

        return convertView;
    }

    public class Holder {
        TextView attendee_name, attendee_title, message_date, attendee_msg;
        ImageView msgImg_view, attendeeImage;

        public Holder(View view) {
            attendee_name = (TextView) view.findViewById(R.id.attendee_name);
//            attendee_name.setTypeface(ConstantData.typeface, Typeface.NORMAL);

            msgImg_view = (ImageView) view.findViewById(R.id.message_status_image);
            attendeeImage = (ImageView) view.findViewById(R.id.attendee_image);
            attendee_title = (TextView) view.findViewById(R.id.attendee_title);
            message_date = (TextView) view.findViewById(R.id.message_date);
            attendee_msg = (TextView) view.findViewById(R.id.attendee_msg);
        }
    }
}
