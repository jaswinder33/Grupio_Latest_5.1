package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.util.HashMap;

/**
 * Created by JSN on 22/8/16.
 */
public class LocaleApi extends BaseApiCall {

    public LocaleApi(Context mContext) {
        super(mContext);
    }

    @Override
    public String getEndPoint() {
        String endPoint = mContext.getString(R.string.locale_api) + Preferences.getInstances(mContext).getLocale();
        endPoint += mContext.getString(R.string.api_event) + ConstantData.EVENT_ID;
        endPoint += mContext.getString(R.string.api_format);

        return endPoint;
    }

    @Override
    public void callApi() {
        APIRequest request = new GetRequest();
        String localeValue = request.requestResponse(url, new HashMap<String, String>(),mContext);
        Log.i("API", "LocaleApi");
    }


}
