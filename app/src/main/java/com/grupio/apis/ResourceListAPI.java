package com.grupio.apis;

import android.content.Context;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.PostRequest;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 28/11/16.
 */

public class ResourceListAPI extends BaseAsyncTask<Void, String> {


    public ResourceListAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.all_resources_api);
    }

    @Override
    public String handleBackground(Void... params) {

        Map<String, String> mParamList = new HashMap<>();
        mParamList.put("event_id", ConstantData.EVENT_ID);

        APIRequest apiRequest = new PostRequest();
        String response = apiRequest.requestResponse(url, mParamList, mContext);

        return response;
    }
}
