package com.grupio.venuemaps;

import android.content.Context;

import com.grupio.R;
import com.grupio.base.SingleElementListBaseAdapter;
import com.grupio.data.LiveData;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 2/11/16.
 */

public class LiveFeedAdapter extends SingleElementListBaseAdapter<LiveData> {
    public LiveFeedAdapter(Context context) {
        super(context);
    }

    @Override
    public void getView(int position, SingleElementListBaseAdapter.Holder holder) {
        holder.name.setText(getItem(position).getName());

        if (getItem(position).getImageUrl().equals("")) {

            String url = getItem(position).getUrl();

            if (url.contains("facebook")) {
                mHolder.imageView.setImageResource(R.drawable.ic_facebook_logo);
            } else if (url.contains("twitter")) {
                mHolder.imageView.setImageResource(R.drawable.ic_twitter);
            } else if (url.toLowerCase().contains("linkedin")) {
                mHolder.imageView.setImageResource(R.drawable.ic_linkedin);
            } else if (url.contains("instagram")) {
                holder.imageView.setImageResource(R.drawable.ic_instagram_logo);
            } else if (url.contains("youtube")) {
                mHolder.imageView.setImageResource(R.drawable.ic_youtube_logo);
            } else {
                holder.imageView.setImageResource(R.drawable.ic_live_default);
            }

        } else {
            ImageLoader.getInstance().displayImage(getItem(position).getUrl(), holder.imageView);
        }

    }
}
