package com.grupio.message.apis;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.CookieRequest;
import com.grupio.dao.MessageDAO;
import com.grupio.message.MessageCountWatcher;
import com.grupio.session.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by JSN on 6/7/16.
 */

/**
 * This API mark received messages as read.
 * <p/>
 * Type: GET
 *
 * @param format=json                  <p/>
 *                                     If there are multiple messages to mark as read, then send messages ids comma separated like
 * @param message_id=25138,25139,25140 <p/>
 *                                     for single message id send
 * @param message_id=25138             <p/>
 *                                     <p>
 *                                     following params are sent as cookies with request.
 * @param event_id
 * @param attendee_id                  Sample Link:
 *                                     https://conf.dharanet.com/conf/v1/main/markmessagesread.php?format=json&message_id=25138
 *                                     <p>
 *                                     Response:
 *                                     Success Response:
 *                                     {"status_code":0,"description":"Success"}
 * @link URL: https://conf.dharanet.com/conf/v1/main/markmessagesread.php
 * Params:
 */

public class MarkMsgAsReadAPI extends AsyncTask<String, Void, Boolean> {

    String msgId;
    String threadId;
    private Context mContext;
    private String result;

    public MarkMsgAsReadAPI(Context mContext) {
        this.mContext = mContext;
    }

    public void doCall(String msgId, String threadId) {
        this.msgId = msgId;
        this.threadId = threadId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            executeOnExecutor(THREAD_POOL_EXECUTOR, msgId, threadId);
        } else {
            execute(msgId, threadId);
        }
    }


    @Override
    protected Boolean doInBackground(String... params) {

        String url = mContext.getString(R.string.base_url) + mContext.getString(R.string.mark_msg_as_read_api) + "&message_id=" + params[0];

        APIRequest apiRequest = new CookieRequest(mContext);
        result = apiRequest.requestResponse(url, new HashMap<>(), mContext);

        if (result != null) {
            return parseData(result);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            MessageDAO.getInstance(mContext).updateUnreadMsg(msgId, threadId);
            String messageCounts = MessageDAO.getInstance(mContext).getUnreadMessageCount();
            Preferences.getInstances(mContext).setUnreadMessageCount(messageCounts);
            MessageCountWatcher.getInstance().updateCount();
        }
    }

    public boolean parseData(String str) {
//{"status_code":0,"description":"Success"}
        JSONObject mObj = null;
        try {
            mObj = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mObj != null) {
            String success = "";
            String description = "";
            try {
                success = mObj.getString("status_code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                description = mObj.getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (description.equals("Success") && success.equals("0")) {
                return true;
            }
        }

        return false;
    }

}
