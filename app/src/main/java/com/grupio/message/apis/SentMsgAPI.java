package com.grupio.message.apis;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.dao.MessageDAO;
import com.grupio.data.MessageData;
import com.grupio.message.MessageDataProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JSN on 6/7/16.
 */
public class SentMsgAPI extends AsyncTask<String, Void, Boolean> {

    private Context mContext;
    private String result;
    private APICallBack mListener;

    public SentMsgAPI(Context mContext) {
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
//            Toast.makeText(mContext, "You are offline", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected Boolean doInBackground(String... params) {

        //   "&page_num=0&threads_per_page="+ Preferences.getInstances(mContext).getTotalMessages()+ "&sent_messages=1";

        String url = mContext.getString(R.string.base_url) + mContext.getString(R.string.message_sent_api) + "&page_num=0&threads_per_page=10&sent_messages=1";// + params[0];
        APIRequest apiRequest = new CookieRequest(mContext);
        result = apiRequest.requestResponse(url, new HashMap<>(), mContext);


        if (result != null) {

            MessageDataProcessor mProcessor = new MessageDataProcessor(mContext);

            List<MessageData> messageList = new ArrayList<>();
            messageList.addAll(mProcessor.parseData(result));

            if (messageList != null && !messageList.isEmpty()) {
                MessageDAO.getInstance(mContext).insertData(result);
                return true;
            }

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
