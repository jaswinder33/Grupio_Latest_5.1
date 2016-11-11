package com.grupio.speakers;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.base.BaseListAdapter;
import com.grupio.dao.EventDAO;
import com.grupio.data.SpeakerData;
import com.grupio.db.EventTable;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 21/10/16.
 */

public class SpeakerAdapter extends BaseListAdapter<SpeakerData, SpeakerAdapter.ViewHolder> {

    boolean hideSpeakerImage = false;

    public SpeakerAdapter(Context context) {
        super(context);
        hideSpeakerImage = EventDAO.getInstance(context).getValue(EventTable.HIDE_SPEAKER_IMAGES).equals("n");
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

        if (hideSpeakerImage) {
            mHolder.image.setVisibility(View.VISIBLE);
            mHolder.presenceTextView.setVisibility(View.GONE);

            String imgUrl = "";
            imgUrl = getItem(position).getImageURL();

            mHolder.image.setTag(imgUrl);

            try {
                ImageLoader.getInstance().displayImage(imgUrl, mHolder.image, Utility.getDisplayOptionsAttendee());
            } catch (Exception e) {
            }
        } else {
            mHolder.image.setVisibility(View.GONE);
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
