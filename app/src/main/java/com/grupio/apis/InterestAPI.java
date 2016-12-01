package com.grupio.apis;

import android.content.Context;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 1/12/16.
 */

public class InterestAPI extends BaseAsyncTask<Void, Boolean> {

    public InterestAPI(Context mcontext) {
        super(mcontext);
    }

    public InterestAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.interest_api);
    }

    @Override
    public Boolean handleBackground(Void... params) {

        Map<String, String> mParam = new HashMap<>();
        mParam.put("event_id", ConstantData.EVENT_ID);

        APIRequest apiRequest = new CookieRequest(mContext);
        String response = apiRequest.requestResponse(url, mParam, mContext);

        if (response != null) {
            Preferences.getInstances(mContext).setInterest(response);
            return true;
        }

        return false;
    }
}
