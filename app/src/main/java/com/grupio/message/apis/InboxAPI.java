package com.grupio.message.apis;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.apis.BaseAsyncTask;
import com.grupio.dao.MessageDAO;
import com.grupio.data.MessageData;
import com.grupio.message.MessageDataProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JSN on 6/7/16.
 */


/**
 * API fetches all inbox message of logged in attendee.
 *
 * @param format=json         returns response in JSON format
 * @param threads_per_page=10 value hardcoded
 * @param page_num            value set runtime.
 *                            <p/>
 *                            following params are sent as cookies with request.
 * @param event_id
 * @param attendee_id         Sample URL:http://conf.dharanet.com/conf/v1/main/getmessages.php?format=json&threads_per_page=10&page_num=0
 * @type GET API
 * @Link http://conf.dharanet.com/conf/v1/main/getmessages.php?format=json
 */
public class InboxAPI extends BaseAsyncTask<String, Boolean> {

    public InboxAPI(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.message_inbox_api);
    }

    @Override
    public Boolean handleBackground(String... params) {

        url += params[0];

        APIRequest apiRequest = new CookieRequest();
        String result = apiRequest.requestResponse(url, new HashMap<>(), mContext);

        if (result != null && !TextUtils.isEmpty(result)) {
//            MessageDAO.getInstance(mContext).deleteMsgs();
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

}
