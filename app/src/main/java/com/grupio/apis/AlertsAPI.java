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
 * https://conf.dharanet.com/conf/v1/main/getnotifications.php?event_id=151&device_id=353720052682941&format=json
 */
public class AlertsAPI extends BaseAsyncTask<Void, Boolean> {

    public AlertsAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        url = mContext.getString(R.string.alerts_api) + ConstantData.EVENT_ID;
        url += "&device_id=" + Preferences.getInstances(mContext).getDeviceID();
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
