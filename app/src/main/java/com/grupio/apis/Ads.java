package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.AdsDAO;
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
    public String getEndPoint() {
        return mContext.getResources().getString(R.string.ads_api) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {

        APIRequest request = new GetRequest();
        String result = request.requestResponse(url, new HashMap<>(), mContext);

        if (result != null) {
            AdsDAO.getInstance(mContext).insert(mContext, result);
        }

        Log.i("API", "Ads API");
    }
}
