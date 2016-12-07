package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.Preferences;

/**
 * Created by JSN on 5/12/16.
 */

public class PhotoGalleryAPI extends BaseAsyncTask<Void, String> {

    public PhotoGalleryAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.photogallery_list);
    }

    @Override
    public String handleBackground(Void... params) {

        APIRequest mApiRequest = new CookieRequest(mContext);
        String response = mApiRequest.makeRequest(url, "");

        if (response != null && !TextUtils.isEmpty(response)) {
            Preferences.getInstances(mContext).savePhotogalleryData(response);
            return response;
        }

        return null;
    }
}
