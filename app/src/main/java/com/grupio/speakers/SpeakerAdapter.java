package com.grupio.speakers;

import android.content.Context;
import android.view.View;

import com.grupio.attendee.ListBaseAdapter;
import com.grupio.attendee.SetSpeakerData;
import com.grupio.dao.EventDAO;
import com.grupio.data.SpeakerData;
import com.grupio.db.EventTable;

/**
 * Created by JSN on 21/10/16.
 */


public class SpeakerAdapter extends ListBaseAdapter<SpeakerData> {

    boolean hideSpeakerImage = false;

    public SpeakerAdapter(Context context) {
        super(context);
        hideSpeakerImage = EventDAO.getInstance(context).getValue(EventTable.HIDE_SPEAKER_IMAGES).equals("n");
    }

    @Override
    public void handleGetView(int position, View view, ViewHolder mHolder) {
        mHolder.mButton.setVisibility(View.GONE);
        SetSpeakerData<SpeakerAdapter> mSetSpeakerData = new SetSpeakerData(getContext());
        mSetSpeakerData.setAdapter(this);
        mSetSpeakerData.setHideSpeakerImage(hideSpeakerImage).isFirstName(isFirstName).setData(getItem(position), mHolder);
    }
}
/*public class SpeakerAdapter extends BaseListAdapter<SpeakerData, SpeakerAdapter.ViewHolder> {

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
    public int getLayout(int position) {
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
    public ViewHolder setViewHolder(View convertView, int position) {
        return new ViewHolder(convertView);
    }

    public static class ViewHolder implements BaseHolder {
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
}*/
