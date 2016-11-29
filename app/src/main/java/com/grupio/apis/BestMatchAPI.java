package com.grupio.apis;

import android.content.Context;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 28/11/16.
 */

public class BestMatchAPI extends BaseAsyncTask<Void, Boolean> {

    public BestMatchAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.best_match_api);
    }

    @Override
    public Boolean handleBackground(Void... params) {

        Map<String, String> paramlist = new HashMap<>();
        paramlist.put("event_id", ConstantData.EVENT_ID);

        APIRequest apiRequest = new CookieRequest(mContext);
        String response = apiRequest.requestResponse(url, paramlist, mContext);


        if (response != null) {
            ((APICallBackWithResponse) mListener).onSuccess(response);
        }

        return null;
    }
}
