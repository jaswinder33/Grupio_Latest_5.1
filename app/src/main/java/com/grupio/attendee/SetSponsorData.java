package com.grupio.attendee;

import android.content.Context;

import com.grupio.R;
import com.grupio.base.BaseSetData;
import com.grupio.data.SponsorData;
import com.grupio.helper.SponsorHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 8/12/16.
 */

public class SetSponsorData<T> extends BaseSetData<SponsorData, SponsorAdapter.Holder, T> {

    public SetSponsorData(Context mContext) {
        super(mContext);
    }

    @Override
    public void setData(SponsorData data, SponsorAdapter.Holder mHolder) {
        mHolder.name.setText(data.name);
        String url = getContext().getString(R.string.base_url) + "/" + data.url;
        ImageLoader.getInstance().displayImage(url, mHolder.imageView, SponsorHelper.sponsorDisplayOptions());
    }
}
