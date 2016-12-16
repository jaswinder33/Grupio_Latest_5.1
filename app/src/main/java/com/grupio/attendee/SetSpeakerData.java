package com.grupio.attendee;

import android.content.Context;
import android.view.View;

import com.grupio.Utils.Utility;
import com.grupio.base.BaseSetData;
import com.grupio.data.SpeakerData;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 8/12/16.
 */

public class SetSpeakerData<T> extends BaseSetData<SpeakerData, ListBaseAdapter.ViewHolder, T> {

    private boolean hideSpeakerImage = false;
    private boolean isFirstName = false;

    public SetSpeakerData(Context mContext) {
        super(mContext);
    }

    @Override
    public void setData(SpeakerData data, ListBaseAdapter.ViewHolder mHolder) {

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

        if (hideSpeakerImage) {
            mHolder.image.setVisibility(View.VISIBLE);
            mHolder.presenceTextView.setVisibility(View.GONE);

            String imgUrl = "";
            imgUrl = data.getImageURL();

            mHolder.image.setTag(imgUrl);

            try {
                ImageLoader.getInstance().displayImage(imgUrl, mHolder.image, Utility.getDisplayOptionsAttendee());
            } catch (Exception e) {
            }
        } else {
            mHolder.image.setVisibility(View.GONE);
        }
    }

    public SetSpeakerData setHideSpeakerImage(boolean hideSpeakerImage) {
        this.hideSpeakerImage = hideSpeakerImage;
        return this;
    }

    public SetSpeakerData isFirstName(boolean flag) {
        isFirstName = flag;
        return this;
    }
}
