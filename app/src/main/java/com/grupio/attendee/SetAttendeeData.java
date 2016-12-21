package com.grupio.attendee;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.base.BaseSetData;
import com.grupio.dao.AttendeeDAO;
import com.grupio.data.AttendeesData;
import com.grupio.db.AttendeeTable;
import com.grupio.session.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 8/12/16.
 */

public class SetAttendeeData<T> extends BaseSetData<AttendeesData, ListBaseAdapter.ViewHolder, T> {

    private boolean showAttendeeImage = false;
    private boolean enableChat = false;
    private boolean isFirstName = false;

    public SetAttendeeData(Context mContext) {
        super(mContext);
    }

    @Override
    public void setData(AttendeesData data, ListBaseAdapter.ViewHolder mHolder) {

        Log.i("SetAttendeeData", "setData: " + data.getAttendee_id());

        mHolder.mButton.setVisibility(View.GONE);

        int position = getPosition(data);
        if (isFirstName) {
            mHolder.name.setText(data.getFirst_name() + " " + data.getLast_name());
        } else {
            mHolder.name.setText(data.getLast_name() + ", " + data.getFirst_name());
        }

        String title = data.getTitle();
        String company = data.getCompany();

        if (company != null && !company.equals("") && title != null && !title.equals("")) {
            title += ", " + company;
        } else {
            title += company;
        }

        mHolder.title.setText(title);

        if (showAttendeeImage) {
            mHolder.image.setVisibility(View.VISIBLE);

            String imgUrl = "";
            if (data.getAttendee_id().equals(Preferences.getInstances(getContext()).getAttendeeId())) {
                mHolder.presenceTextView.setVisibility(View.GONE);
            }

            imgUrl = getContext().getString(R.string.base_url) + AttendeeDAO.getInstance(getContext()).getValue(AttendeeTable.IMAGE, data.getAttendee_id());
            mHolder.image.setTag(imgUrl);

            try {
                ImageLoader.getInstance().displayImage(imgUrl, mHolder.image, Utility.getDisplayOptionsAttendee());
            } catch (Exception e) {
            }

        } else {
            mHolder.image.setVisibility(View.GONE);

        }

        if (enableChat) {

            if (Preferences.getInstances(getContext()).getUserInfo() == null ||
                    getItem(position).getAttendee_id().equalsIgnoreCase(Preferences.getInstances(getContext()).getAttendeeId())) {
                mHolder.presenceTextView.setVisibility(View.GONE);
            } else {
                mHolder.presenceTextView.setVisibility(View.VISIBLE);

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

        } else {
            mHolder.presenceTextView.setVisibility(View.GONE);
        }
    }

    public SetAttendeeData enableChat(boolean flag) {
        enableChat = flag;
        return this;
    }

    public SetAttendeeData setShowAttendeeImage(boolean flag) {
        showAttendeeImage = flag;
        return this;
    }

    public SetAttendeeData isFirstName(boolean flag) {
        isFirstName = flag;
        return this;
    }
}
