package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.LiveDAO;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;

import java.util.HashMap;

/**
 * Created by JSN on 2/11/16.
 */

public class LiveAPI extends BaseAsyncTask<Void, Boolean> {

    private String response;

    public LiveAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.live_api) + ConstantData.EVENT_ID;
    }

    @Override
    public Boolean handleBackground(Void... params) {

        APIRequest api = new GetRequest();
        response = api.requestResponse(url, new HashMap<>(), mContext);

        if (response != null && !TextUtils.isEmpty(response)) {
            LiveDAO.getInstance(mContext).insertData(response);
            return true;
        }

        return false;

    }

//    @Override
//    protected void onPostExecute(Boolean mResponse) {
//        super.onPostExecute(mResponse);
//        if (mResponse) {
//            ((APICallBackWithResponse) mListener).onSuccess(response);
//        } else {
//            mListener.onFailure(mContext.getString(R.string.erroor_occured));
//        }
//    }

}
