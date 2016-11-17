package com.grupio.apis;

import android.content.Context;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 8/11/16.
 */

public class LikeUnlikeSessionAPI extends BaseAsyncTask<String, Void> {
    public LikeUnlikeSessionAPI(Context mcontext) {
        super(mcontext);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.like_unlike_schedule_api);
    }

    @Override
    public Void handleBackground(String... params) {

        String operation = params[0];
        String sessionId = params[1];

        Map<String, String> mParamList = new HashMap<>();
        mParamList.put("method", operation);
        mParamList.put("session_id", sessionId);
        mParamList.put("event_id", ConstantData.EVENT_ID);
        mParamList.put("device_id", Preferences.getInstances(mContext).getDeviceID());
        mParamList.put("device_token", Preferences.getInstances(mContext).getDeviceToken());

        APIRequest apiRequest = new CookieRequest(mContext);
        apiRequest.requestResponse(url, mParamList, mContext);

        return null;
    }

    public boolean parseResponse(String response) {

        boolean isSuccessfull = false;

        JSONObject mJsonObj = null;
        try {
            mJsonObj = new JSONObject(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mJsonObj != null) {

            try {
                isSuccessfull = mJsonObj.getString("status_code").equals("0");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return isSuccessfull;
    }

}
