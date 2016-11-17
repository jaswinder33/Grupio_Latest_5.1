package com.grupio.message.apis;

import android.content.Context;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.apis.BaseAsyncTask;
import com.grupio.dao.MessageDAO;
import com.grupio.data.MessageData;
import com.grupio.message.MessageDataProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * API used to send message to other attendee (from Message menu only)) within same thread.
 *
 * @link http://conf.dharanet.com/conf/v1/main/sendmessage.php
 * @param title title of thread
 * @param content message to be sent
 * @param receiver_id id of receiver attendee
 * @param thread_id thread id to which this message belongs
 * @param format=JSON returns response as JSON formatted
 * <p>
 * following params are sent as cookies with request.
 * @param event_id
 * @param attendee_id
 * <p>
 * Above params are sent from UI through MessageData object in doCall() method
 * <p>
 * response:
 */

/**
 * Created by JSN on 6/7/16.
 */
public class SendMsgAPI extends BaseAsyncTask<MessageData, Boolean> {

    public SendMsgAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.send_msg_api);
    }

    @Override
    public Boolean handleBackground(MessageData... params) {

        MessageData mMessageData = params[0];

        Map<String, String> paramList = new HashMap<>();
        paramList.put("title", mMessageData.getTitle());
        paramList.put("content", mMessageData.getContent());
        paramList.put("receiver_id", mMessageData.getReceiver_id());
        paramList.put("thread_id", mMessageData.getThread_id());
        paramList.put("format", "json");

        APIRequest apiCall = new CookieRequest(mContext);
        String result = apiCall.requestResponse(url, paramList, mContext);

        if (result != null) {

            MessageDataProcessor mdp = new MessageDataProcessor(mContext);
            String msgId = mdp.getMessageId(result);

            if (msgId != null) {
                String utcTime = mMessageData.gettime();
                mMessageData.setDatetime(Utility.convertUTCtoMyTime(utcTime));
                mMessageData.setMessage_id(msgId);
                MessageDAO.getInstance(mContext).insertData(mMessageData);
                return true;
            }
        }
        return false;


    }
}
