package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.BuildConfig;
import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.PostRequest;
import com.grupio.data.AttendeesData;
import com.grupio.helper.AttendeeProcessor;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JSN on 14/10/16.
 */

public class LoginApi extends BaseAsyncTask<String, Boolean> {
    public LoginApi(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.login_api);
    }

    @Override
    public Boolean handleBackground(String... params) {
        Map<String, String> paramList = new HashMap<>();
        paramList.put("email_address", params[0]);
        paramList.put("confirmation_code", params[1]);
        paramList.put("event_id", ConstantData.EVENT_ID);
        paramList.put("organization_id", BuildConfig.ORG_ID);
        paramList.put("device_id", ConstantData.DEVICE_ID);
        paramList.put("device_token", ConstantData.DEVICE_TOKEN);
        paramList.put("device", "android");

        APIRequest apiRequest = new PostRequest();
        String response = apiRequest.requestResponse(url, paramList, mContext);

        if (response != null && !TextUtils.isEmpty(response)) {
            AttendeeProcessor attendeeProcessor = new AttendeeProcessor();
            List<AttendeesData> attendeesData = attendeeProcessor.getAttendeesListFromJSON(mContext, response, false);
            Preferences.getInstances(mContext).saveUserInfo(response);
            Preferences.getInstances(mContext).setAttendeeId(attendeesData.get(0).getAttendee_id());
            return true;
        }
        return false;
    }
}
