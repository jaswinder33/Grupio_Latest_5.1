package com.grupio.message;

import android.content.Context;

import com.grupio.Utils.Utility;
import com.grupio.data.MessageData;
import com.grupio.session.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 7/7/16.
 */
public class MessageDataProcessor {

    private Context mContext;

    public MessageDataProcessor(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Parse JSON response returned from Inbox/Sent/LoadMore APIs and persist in db
     *
     * @param response response sent from API call.
     * @return value returned after processing on response
     */
    public List<MessageData> parseData(String response) {

        List<MessageData> messageDataList = new ArrayList<>();

        JSONObject mObj = null;
        try {
            mObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mObj != null) {
            JSONArray mDataArray = null;
            try {
                mDataArray = mObj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mDataArray != null && mDataArray.length() > 0) {

                MessageData mData;
                for (int i = 0; i < mDataArray.length(); i++) {
                    mData = new MessageData();

                    try {
                        mData.setMessage_id(mDataArray.getJSONObject(i).getString("message_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setThread_id(mDataArray.getJSONObject(i).getString("thread_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setSender_id(mDataArray.getJSONObject(i).getString("sender_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setReceiver_id(mDataArray.getJSONObject(i).getString("receiver_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setIs_unread(mDataArray.getJSONObject(i).getString("is_unread"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {

                        mData.setDatetime(Utility.convertUTCtoMyTime(mDataArray.getJSONObject(i).getString("datetime")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setFolder(mDataArray.getJSONObject(i).getString("folder"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setTitle(mDataArray.getJSONObject(i).getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setContent(mDataArray.getJSONObject(i).getString("content"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setSender_email(mDataArray.getJSONObject(i).getString("sender_email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setSender_first_name(mDataArray.getJSONObject(i).getString("sender_first_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setSender_last_name(mDataArray.getJSONObject(i).getString("sender_last_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setReceiver_email(mDataArray.getJSONObject(i).getString("receiver_email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setReceiver_first_name(mDataArray.getJSONObject(i).getString("receiver_first_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setReceiver_last_name(mDataArray.getJSONObject(i).getString("receiver_last_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    messageDataList.add(mData);

                }
            }
        }
        return messageDataList;
    }

    /**
     * Parse response received with MessageCountAPI
     *
     * @param response response sent from MessageCountAPI call
     * @return boolean status of successfull processing of response
     * @response {"data":{"UnreadMessages":"1","TotalMessages":"2"}}
     */
    public Boolean parseCountAPI(String response) {

//{"data":{"UnreadMessages":"1","TotalMessages":"2"}}
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response).getJSONObject("data");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (jObj != null) {
            try {
                Preferences.getInstances(mContext).setUnreadMessageCount(jObj.getString("UnreadMessages"));
                Preferences.getInstances(mContext).setTotaldMessageCount(jObj.getString("TotalMessages"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * Parse response received with SendMsgAPI and return message id within the response
     *
     * @param response response sent from SendMsgAPI call
     * @return String  message id within the response
     */
    public String getMessageId(String response) {

        JSONObject mObj = null;
        try {
            mObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mObj != null) {
            try {
                return mObj.getString("message_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
