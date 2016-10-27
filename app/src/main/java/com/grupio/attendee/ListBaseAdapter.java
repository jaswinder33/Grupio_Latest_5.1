package com.grupio.attendee;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.BaseListAdapter;

/**
 * Created by JSN on 21/10/16.
 */

public abstract class ListBaseAdapter<Person> extends BaseListAdapter<Person, ListBaseAdapter.ViewHolder> {


    public ListBaseAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.layout_attendee_list_child;
    }

    @Override
    public ListBaseAdapter.ViewHolder setViewHolder(View convertView) {
        return new ListBaseAdapter.ViewHolder(convertView);
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
