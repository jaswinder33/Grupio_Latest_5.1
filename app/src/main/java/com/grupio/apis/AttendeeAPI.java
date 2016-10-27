package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.AttendeeDAO;
import com.grupio.session.ConstantData;

import java.util.HashMap;

/**
 * Created by JSN on 1/9/16.
 */
public class AttendeeAPI extends BaseApiCall {

    public AttendeeAPI(Context context) {
        super(context);
    }

    @Override
    public String getEndPoint() {
        return mContext.getResources().getString(R.string.attendee_api) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {
        APIRequest request = new GetRequest();
        String response = request.requestResponse(url, new HashMap<String, String>(), mContext);

        if (response != null) {
            AttendeeDAO.getInstance(mContext).insert(response);
        }

        Log.i("API", "AttendeeListAPI");
    }
}
