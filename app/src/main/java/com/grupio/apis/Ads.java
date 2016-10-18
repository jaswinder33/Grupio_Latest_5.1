package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.session.ConstantData;

import java.util.HashMap;

/**
 * Created by JSN on 1/9/16.
 */
public class Ads extends BaseApiCall {

    public Ads(Context context) {
        super(context);
    }

    @Override
    public void run() {
        super.run();


        String url = ConstantData.ADS_API + ConstantData.EVENT_ID + ConstantData.API_FORMAT;

        APIRequest request = new GetRequest();
        String result = request.requestResponse(url, new HashMap<String, String>(), mContext);

        if (result != null){

        }


        Log.i("API", "Ads API");

    }
}
