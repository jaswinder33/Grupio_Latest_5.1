package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.SpeakerDAO;
import com.grupio.session.ConstantData;

import java.util.HashMap;


/**
 * Created by JSN on 1/9/16.
 */
public class SpeakerAPI extends BaseApiCall{

    public SpeakerAPI(Context Context){ super(Context);}

    @Override
    public String getEndPoint() {
        return mContext.getString(R.string.speakers_api) + ConstantData.EVENT_ID;
    }

    @Override
    public void callApi() {

        APIRequest request = new GetRequest();
        String response = request.requestResponse(url, new HashMap<>(), mContext);

        if (response != null && !TextUtils.isEmpty(response)) {
//            //insert new speakers in db
            SpeakerDAO.getInstance(mContext).insert(response);
        }

        Log.i("API", "SpeakersAPI");
    }
}
