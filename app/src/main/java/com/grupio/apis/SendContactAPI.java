package com.grupio.apis;

import android.content.Context;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by JSN on 3/8/16.
 */
public class SendContactAPI extends BaseAsyncTask<String, Boolean> {


    public SendContactAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.send_contact_api);
    }

    @Override
    public Boolean handleBackground(String... params) {

        url = url + "&event_id=" + ConstantData.EVENT_ID + "&sendto=" + params[0];
        APIRequest apiRequest = new CookieRequest(mContext);
        String response = apiRequest.requestResponse(url, new HashMap<>(), mContext);

//{"description":"Success"}

        JSONObject mObj = null;
        try {
            mObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mObj != null) {
            try {
                return mObj.getString("description").equals("Success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
