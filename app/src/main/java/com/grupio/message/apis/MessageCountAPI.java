package com.grupio.message.apis;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.message.MessageDataProcessor;

import java.util.HashMap;

/**
 * This api returns unread and total message count from server.
 *
 * @link http://conf.dharanet.com/conf/v1/main/getmessagecount.php
 * @param format=json returns response JSON formatted.
 * <p>
 * <p>
 * following params are sent as cookies with request.
 * @param event_id
 * @param attendee_id
 * @response JSON Format {"data":{"UnreadMessages":"0","TotalMessages":"0"}}
 */

/**
 * Created by JSN on 6/7/16.
 */
public class MessageCountAPI extends AsyncTask<Void, Void, Boolean> {

    private Context mContext;
    private String result;
    private APICallBack mListener;

    public MessageCountAPI(Context mContext) {
        this.mContext = mContext;
    }

    public void doCall(APICallBack mListener) {
        this.mListener = mListener;

        if (Utility.hasInternet(mContext)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                executeOnExecutor(THREAD_POOL_EXECUTOR);
            } else {
                execute();
            }
        } else {
            mListener.onFailure("You are offline");
        }
    }


    @Override
    protected Boolean doInBackground(Void... params) {
//http://conf.dharanet.com/conf/v1/main/getmessagecount.php?format=json

        String url = mContext.getString(R.string.base_url) + mContext.getString(R.string.message_count);

        APIRequest apiRequest = new CookieRequest();
        result = apiRequest.requestResponse(url, new HashMap<>(), mContext);

//{"data":{"UnreadMessages":"0","TotalMessages":"0"}}
        if (result != null) {
            MessageDataProcessor mProcessor = new MessageDataProcessor(mContext);
            return mProcessor.parseCountAPI(result);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (aBoolean) {
            if (mListener != null) {
                mListener.onSuccess();
            }
        } else {
            if (mListener != null) {
                mListener.onFailure(null);
            }
        }
    }


}
