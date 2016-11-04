package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.PostRequest;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 4/11/16.
 */

public class ExhibitorCategoryAPI extends BaseAsyncTask<Void, Void> {

    public ExhibitorCategoryAPI(Context mcontext) {
        super(mcontext);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.exhibitor_category_api);
    }

    @Override
    public Void handleBackground(Void... params) {

        Map<String, String> paramList = new HashMap<>();
        paramList.put("event_id", ConstantData.EVENT_ID);

        APIRequest apiRequest = new PostRequest();
        String response = apiRequest.requestResponse(url, paramList, mContext);

        if (response != null && !TextUtils.isEmpty(response)) {
            Preferences.getInstances(mContext).setExhibitorCategory(response);
        }

        Log.i("API", "ExhibitorCategoryAPI");

        return null;
    }
}


