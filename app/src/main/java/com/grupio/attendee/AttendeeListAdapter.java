package com.grupio.attendee;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.base.BaseListAdapter;
import com.grupio.dao.AttendeeDAO;
import com.grupio.dao.EventDAO;
import com.grupio.dao.MenuDAO;
import com.grupio.data.AttendeesData;
import com.grupio.db.AttendeeTable;
import com.grupio.db.EventTable;
import com.grupio.session.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 25/7/16.
 */
public class AttendeeListAdapter extends BaseListAdapter<AttendeesData, AttendeeListAdapter.ViewHolder> {

    boolean showImage = false;
    boolean isChatAvailable = false;

    public AttendeeListAdapter(Context context) {
        super(context);
        showImage = EventDAO.getInstance(getContext()).getValue(EventTable.HIDE_ATTENDEE_IMAGES).equalsIgnoreCase("n");
        isChatAvailable = MenuDAO.getInstance(getContext()).checkIfMenuExists("chat");
    }

    @Override
    public String getFirstName(int position) {
        return getItem(position).getFirst_name();
    }

    @Override
    public String getLastName(int position) {
        return getItem(position).getLast_name();
    }

    @Override
    public int getLayout() {
        return R.layout.layout_attendee_list_child;
    }

    @Override
    public void handleGetView(int position, ViewHolder mHolder) {

        if (isFirstName) {
            mHolder.name.setText(getItem(position).getFirst_name() + " " + getItem(position).getLast_name());
        } else {
            mHolder.name.setText(getItem(position).getLast_name() + ", " + getItem(position).getFirst_name());
        }

        String title = getItem(position).getTitle();
        String company = getItem(position).getCompany();


        if (company != null && !company.equals("") && title != null && !title.equals("")) {
            title += ", " + company;
        } else {
            title += company;
        }

        mHolder.title.setText(title);

        if (showImage) {
            mHolder.image.setVisibility(View.VISIBLE);

            String imgUrl = "";
            if (getItem(position).getAttendee_id().equals(Preferences.getInstances(getContext()).getAttendeeId())) {
                mHolder.presenceTextView.setVisibility(View.GONE);
            }

            imgUrl = getContext().getString(R.string.base_url) + AttendeeDAO.getInstance(getContext()).getValue(AttendeeTable.IMAGE, getItem(position).getAttendee_id());
            mHolder.image.setTag(imgUrl);

            try {
                ImageLoader.getInstance().displayImage(imgUrl, mHolder.image, Utility.getDisplayOptionsAttendee());
            } catch (Exception e) {
            }

        } else {
            mHolder.image.setVisibility(View.GONE);

        }


        if (Preferences.getInstances(getContext()).getUserInfo() == null ||
                getItem(position).getAttendee_id().equalsIgnoreCase(Preferences.getInstances(getContext()).getAttendeeId())) {
            mHolder.presenceTextView.setVisibility(View.GONE);
        } else {

            if (isChatAvailable) {
                mHolder.presenceTextView.setVisibility(View.VISIBLE);
            } else {
                mHolder.presenceTextView.setVisibility(View.GONE);
            }


            if (getItem(position).isExistInFriendTable) {
                if (getItem(position).isFriend.equalsIgnoreCase("true")) {
                    try {
                        if (getItem(position).presence_status != null && getItem(position).presence_status.equalsIgnoreCase("online")) {
//                            holder.presenceTextView.setBackgroundResource(R.drawable.circle_green);
                            mHolder.presenceTextView.setBackgroundResource(R.drawable.circle);
                        } else {
                            mHolder.presenceTextView.setBackgroundResource(R.drawable.circle);
//                            holder.presenceTextView.setBackgroundResource(R.drawable.circle_red);
                        }
                    } catch (Exception e) {
                        mHolder.presenceTextView.setBackgroundResource(R.drawable.circle);
//                        holder.presenceTextView.setBackgroundResource(R.drawable.circle_red);
                    }

                    mHolder.presenceTextView.setText("");
                } else {
                    mHolder.presenceTextView.setText("?");
                }


            } else {
                mHolder.presenceTextView.setBackgroundResource(R.drawable.circle);
//                holder.presenceTextView.setBackgroundResource(R.drawable.grey_round);
                mHolder.presenceTextView.setText("+");
            }
        }
    }

    @Override
    public ViewHolder setViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    public class ViewHolder {
        public TextView name, title;
        public ImageView image;
        public TextView presenceTextView;
        public ImageButton mButton;

        public ViewHolder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.attendee_name);
            title = (TextView) convertView.findViewById(R.id.attendee_company_title);
            image = (ImageView) convertView.findViewById(R.id.attendee_image);
            presenceTextView = (TextView) convertView.findViewById(R.id.presenceTextView);

            mButton = (ImageButton) convertView.findViewById(R.id.addItem);
        }
    }
}
