package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.EventDAO;
import com.grupio.session.ConstantData;

import java.util.HashMap;


/**
 * Created by JSN on 22/8/16.
 */
public class EventDetailAPI extends BaseApiCall {

    public EventDetailAPI(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public String getEndPoint() {
        return mContext.getString(R.string.event_details) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {
        APIRequest request = new GetRequest();
        String result = request.requestResponse(url, new HashMap<>(), mContext);

        Log.i("API", "EventDetailAPI");

        if (result != null) {
            EventDAO.getInstance(mContext).insert(result);
        }
    }

}
