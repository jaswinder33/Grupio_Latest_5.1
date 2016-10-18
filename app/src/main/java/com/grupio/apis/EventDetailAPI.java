package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.EventDAO;
import com.grupio.session.ConstantData;

import java.util.HashMap;


/**
 * Created by JSN on 22/8/16.
 */
public class EventDetailAPI extends BaseApiCall {

    private String result;
//    protected Context mContext;


    public EventDetailAPI(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void run() {
        super.run();

        String eventUrl = ConstantData.EVENT_DETAILS_API + ConstantData.EVENT_ID + ConstantData.API_FORMAT;

        APIRequest request = new GetRequest();
        String result = request.requestResponse(eventUrl, new HashMap<String, String>(), mContext);

        Log.i("API", "EventDetailAPI");

        if (result != null) {
            EventDAO.getInstance(mContext).insert(result);
        }
    }

}
