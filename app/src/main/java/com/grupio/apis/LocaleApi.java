package com.grupio.apis;

import android.content.Context;
import android.util.Log;

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
    public void run() {
        super.run();

        String url = ConstantData.LOCALE_API + Preferences.getInstances(mContext).getLocale() + ConstantData.API_EVENT + ConstantData.EVENT_ID + ConstantData.API_FORMAT;

        APIRequest request = new GetRequest();
        String localeValue = request.requestResponse(url, new HashMap<String, String>(),mContext);

//        String localeValue = GridHome.ut_obj.postData(url,new ArrayList<NameValuePair>(),mContext);
//
//        if(localeValue != null){
//            LocalisationDataProcessor.parseJson(localeValue);
//        }

        Log.i("API", "LocaleApi");

    }


}
