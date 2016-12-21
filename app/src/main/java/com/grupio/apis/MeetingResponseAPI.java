package com.grupio.apis;

import android.content.Context;

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
 * Created by JSN on 20/12/16.
 */

/**
 * This api update status of meeting which is created having logged in user as invitee.
 */
public class MeetingResponseAPI extends BaseAsyncTask<Object, Boolean> {

    public MeetingResponseAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.meeting_api);
    }

    @Override
    public Boolean handleBackground(Object... params) {

        MeetingData mMeetingData = (MeetingData) params[0];
        String status = (String) params[1];

        Map<String, String> paramList = new HashMap<>();
        paramList.put("method", "response");
        paramList.put("event_id", ConstantData.EVENT_ID);
        paramList.put("meeting_id", mMeetingData.id);
        paramList.put("slot1", status);

        APIRequest api = new CookieRequest(mContext);
        String response = api.requestResponse(url, paramList, mContext);

        if (response != null) {
            return parseResponse(response);
        }

        return false;
    }

    public boolean parseResponse(String response) {

//        {"status_code":0,"description":"Success"}

        boolean status = false;

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (jsonObject != null) {
            try {
                status = jsonObject.getString("status_code").equals("0");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return status;

    }
}
