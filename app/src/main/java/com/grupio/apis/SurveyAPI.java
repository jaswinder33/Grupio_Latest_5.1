package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.session.ConstantData;

import java.util.HashMap;


/**
 * Created by JSN on 22/8/16.
 */
public class SurveyAPI extends BaseApiCall {

    public SurveyAPI(Context mContext) {
        super(mContext);
    }

    @Override
    public String getEndPoint() {
        return mContext.getString(R.string.survey_apis) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {

        APIRequest request = new GetRequest();
        String response =  request.requestResponse(url, new HashMap<String, String>(), mContext);
        if(response != null){

        }

        Log.i("API", "SurveyAPI");
    }

}
