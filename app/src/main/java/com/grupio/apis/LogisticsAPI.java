package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.LogisticsDAO;
import com.grupio.session.ConstantData;

import java.util.HashMap;

/**
 * Created by JSN on 22/8/16.
 */
public class LogisticsAPI extends BaseApiCall {

    public LogisticsAPI(Context mContext) {
        super(mContext);
    }

    @Override
    public String getEndPoint() {
        return mContext.getString(R.string.logistics_api) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {
        APIRequest request = new GetRequest();
        String result = request.requestResponse(url, new HashMap<>(), mContext);

        if (result != null && !TextUtils.isEmpty(result)) {
            LogisticsDAO.getInstance(mContext).insertData(result);
        }

        Log.i("API", "LogisticsAPI");
    }

}
