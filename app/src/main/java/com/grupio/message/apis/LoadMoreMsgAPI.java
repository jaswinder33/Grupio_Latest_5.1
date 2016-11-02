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
import java.util.Map;


/**
 * API used to load older messages.
 *
 * @type POST
 * @Link https://conf.dharanet.com/conf/v1/main/getmessages.php
 * <p>
 * Parameters
 * @Param format=json
 * @Param page_num=0 initial value(hardcoded).
 * @Param threads_per_page = offset
 * for offset initial value is 10(hardcoded) and increment with 3 with each hit.
 * <p>
 * following params are sent as cookies with request.
 * @param event_id
 * @param attendee_id
 * @response response is returned same as Inbox/Sent api
 */

/**
 * Created by JSN on 6/7/16.
 */
public class LoadMoreMsgAPI extends AsyncTask<Void, Void, Boolean> {

    private Context mContext;
    private String result;
    private APICallBack mListener;
    private int offset = 10;

    public LoadMoreMsgAPI(Context mContext) {
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

        //page_num="+ startMessage+ "&threads_per_page="+ endMessage+ "&sent_messages=1";

        offset = offset + 3;


        String url = mContext.getString(R.string.base_url) + mContext.getString(R.string.load_more_msg_api);

        APIRequest apiRequest = new CookieRequest();

        Map<String, String> paramList = new HashMap<>();
        paramList.put("page_num", "0");
        paramList.put("threads_per_page", "" + offset);

        result = apiRequest.requestResponse(url, paramList, mContext);

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
