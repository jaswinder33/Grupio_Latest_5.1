package com.grupio.apis;

import android.content.Context;

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

public class MarkAlertRead extends BaseAsyncTask<String, Void> {
    public MarkAlertRead(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {

        url = mContext.getString(R.string.mark_alert_read_api) + ConstantData.EVENT_ID;
        url += "&device_id=" + Preferences.getInstances(mContext).getDeviceID();
        url += "&format=json";

        return url;
    }

    @Override
    public Void handleBackground(String... params) {

        url += "&alert_id=" + params[0];

        APIRequest apiRequest = new GetRequest();
        apiRequest.requestResponse(url, new HashMap<>(), mContext);

        return null;
    }
}
