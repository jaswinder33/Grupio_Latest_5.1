package com.grupio.apis;

import android.content.Context;
import android.util.Log;

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
    public void run() {
        super.run();

        String url = ConstantData.SURVEYS_API + ConstantData.EVENT_ID + ConstantData.API_FORMAT;

        APIRequest request = new GetRequest();
        String response =  request.requestResponse(url, new HashMap<String, String>(), mContext);
// String result = GridHome.ut_obj.postData(url, new ArrayList<NameValuePair>(), mContext);

        if(response != null){
        }

        Log.i("API", "SurveyAPI");
    }

}
