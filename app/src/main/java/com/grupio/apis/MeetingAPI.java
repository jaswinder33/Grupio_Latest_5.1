package com.grupio.apis;

import android.content.Context;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.backend.DateTime;
import com.grupio.dao.MeetingDAO;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 12/12/16.
 */

public class MeetingAPI extends BaseAsyncTask<Void, String> {

    public MeetingAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.meeting_api);
    }

    @Override
    public String handleBackground(Void... params) {

        Map<String, String> paramList = new HashMap<>();
        paramList.put("event_id", ConstantData.EVENT_ID);
        paramList.put("method", "showmymeeting");
        paramList.put("currentDate", DateTime.getInstance().getCurrentTime());

        APIRequest apiRequest = new CookieRequest(mContext);
        String response = apiRequest.requestResponse(url, paramList, mContext);

        if (response != null) {

            MeetingDAO.getInstance(mContext).insertData(response);
            return response;
        }

        return null;
    }
}
