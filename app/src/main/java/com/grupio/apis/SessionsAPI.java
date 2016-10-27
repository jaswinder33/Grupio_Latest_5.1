package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.SessionDAO;
import com.grupio.session.ConstantData;

import java.util.HashMap;

/**
 * Created by JSN on 22/8/16.
 */
public class SessionsAPI extends BaseApiCall {

    public SessionsAPI(Context mContext) {
        super(mContext);
    }

    @Override
    public String getEndPoint() {
        return mContext.getString(R.string.sessions_api) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {
        APIRequest request = new GetRequest();
        String response = request.requestResponse(url, new HashMap<>(), mContext);

        if (response != null) {
            SessionDAO.getInstance(mContext).insertData(response);
        }

        Log.i("API", "SessionsAPI");
    }


}
