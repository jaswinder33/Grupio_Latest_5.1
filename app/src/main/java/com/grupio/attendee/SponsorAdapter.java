package com.grupio.attendee;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.base.BaseListAdapter;
import com.grupio.data.SponsorData;
import com.grupio.helper.SponsorHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 21/11/16.
 */

public class SponsorAdapter extends BaseListAdapter<SponsorData, SponsorAdapter.Holder> {

    public SponsorAdapter(Context context) {
        super(context);
    }

    @Override
    public String getFirstName(int position) {
        return getItem(position).type;
    }

    @Override
    public String getLastName(int position) {
        return getItem(position).type;
    }

    @Override
    public int getLayout() {
        return R.layout.layout_sponsor;
    }

    @Override
    public void handleGetView(int position, Holder mHolder) {
        mHolder.name.setText(getItem(position).name);
        String url = getContext().getString(R.string.base_url) + "/" + getItem(position).url;
        ImageLoader.getInstance().displayImage(url, mHolder.imageView, SponsorHelper.sponsorDisplayOptions());
    }

    @Override
    public Holder setViewHolder(View convertView) {
        return new Holder(convertView);
    }

    class Holder {
        public TextView name;
        public ImageView imageView;

        public Holder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.nameSponsor);
            imageView = (ImageView) convertView.findViewById(R.id.imageSponsor);
        }
    }

}
