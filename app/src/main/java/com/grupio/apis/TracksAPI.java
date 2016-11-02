package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.SessionTracksDAO;
import com.grupio.session.Preferences;

import java.util.HashMap;

/**
 * Created by JSN on 20/10/16.
 */

public class TracksAPI extends BaseApiCall {

    public TracksAPI(Context mContext) {
        super(mContext);
    }

    @Override
    public String getEndPoint() {
        return mContext.getString(R.string.tracks_list_api) + Preferences.getInstances(mContext).getEventId();
    }

    @Override
    public void callApi() {
        APIRequest apiRequest = new GetRequest();
        String response = apiRequest.requestResponse(url, new HashMap<>(), mContext);

        if (response != null && !TextUtils.isEmpty(response)) {
            SessionTracksDAO.getInstance(mContext).insertData(response);
        }

        Log.i("TracksAPI", "Tracks API Called ");


    }
}
