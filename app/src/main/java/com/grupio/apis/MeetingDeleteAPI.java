package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.data.MeetingData;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 21/12/16.
 */

public class MeetingDeleteAPI extends BaseAsyncTask<MeetingData, Boolean> {

    public MeetingDeleteAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.meeting_api);
    }

    @Override
    public Boolean handleBackground(MeetingData... params) {
        MeetingData mMeetingData = params[0];

        Map<String, String> paramList = new HashMap<>();
        paramList.put("event_id", ConstantData.EVENT_ID);
        paramList.put("meeting_id", mMeetingData.id);
        paramList.put("method", "delete");

        APIRequest apiRequest = new CookieRequest(mContext);
        String response = apiRequest.requestResponse(url, paramList, mContext);

        if (response != null && !TextUtils.isEmpty(response)) {
            return parseResponse(response);
        }

        return false;
    }

    public boolean parseResponse(String response) {
        JSONObject mResultObj = null;
        try {
            mResultObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mResultObj != null) {
            try {
                if (mResultObj.getInt("status_code") == 0) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return false;
    }
}
