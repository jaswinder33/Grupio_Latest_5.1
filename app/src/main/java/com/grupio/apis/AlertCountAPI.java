package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.util.HashMap;

/**
 * Created by JSN on 27/10/16.
 */

/**
 *
 */
public class AlertCountAPI extends BaseAsyncTask<Void, Boolean> {
    public AlertCountAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {

        url = mContext.getString(R.string.alerts_count_api) + ConstantData.EVENT_ID;
        url += "&device_id=" + Preferences.getInstances(mContext).getDeviceID();
        url += "&device_token=" + Preferences.getInstances(mContext).getDeviceToken();
        url += "&format=json";

        return url;
    }

    @Override
    public Boolean handleBackground(Void... params) {
        APIRequest apiRequest = new GetRequest();
        String response = apiRequest.requestResponse(url, new HashMap<>(), mContext);

        return response != null && !TextUtils.isEmpty(response);
    }
}
