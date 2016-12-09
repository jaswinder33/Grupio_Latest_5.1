package com.grupio.attendee;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.BaseHolder;
import com.grupio.base.BaseListAdapter;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.SpeakerData;

/**
 * Created by JSN on 21/10/16.
 */


/**
 * Base class of adapter for Attendee, Sponsor and Exhibitor adapters
 *
 * @param <T>
 */
public abstract class ListBaseAdapter<T> extends BaseListAdapter<T, ListBaseAdapter.ViewHolder> {

    public ListBaseAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout(int position) {
        return R.layout.layout_attendee_list_child;
    }

    @Override
    public String getFirstName(int position) {
        if (getAdapterType() instanceof AttendeesData) {
            return ((AttendeesData) getItem(position)).getFirst_name();
        } else if (getAdapterType() instanceof SpeakerData) {
            return ((SpeakerData) getItem(position)).getFirst_name();
        } else if (getAdapterType() instanceof ExhibitorData) {
            return ((ExhibitorData) getItem(position)).getName();
        }
        return "";
    }

    @Override
    public String getLastName(int position) {
        if (getAdapterType() instanceof AttendeesData) {
            return ((AttendeesData) getItem(position)).getLast_name();
        } else if (getAdapterType() instanceof SpeakerData) {
            return ((SpeakerData) getItem(position)).getLast_name();
        } else if (getAdapterType() instanceof ExhibitorData) {
            return ((ExhibitorData) getItem(position)).getName();
        }
        return "";
    }


    @Override
    public ListBaseAdapter.ViewHolder setViewHolder(View convertView, int position) {
        return new ListBaseAdapter.ViewHolder(convertView);
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
}
