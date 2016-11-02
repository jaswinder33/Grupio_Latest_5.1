package com.grupio.message;


import com.grupio.data.MessageData;

import java.util.List;

/**
 * Created by JSN on 6/7/16.
 */
public interface ControllerInter {

    void fetchInboxMsgs(OnTaskComplete mListener);

    void fetchSentMsgs(OnTaskComplete mListener);

    void loadMoreMsgs(OnTaskComplete mListener);

    void fetchInboxMsgsFromDb(String query, OnTaskComplete mListener);

    void fetchSentMsgsFromDb(String query, OnTaskComplete mListener);

    void fetchMessageCount(OnTaskComplete mListener);

    void fetchMessageListOfParticularThreadFromDb(String threadId, OnTaskComplete mListener);

    void sendMessage(MessageData msg, OnTaskComplete mListener);

    void markMessageRead(String messageId, String threadId);

    void clearData();

    interface OnTaskComplete {
        void showCount();

        void hideCount();

        //        void onSuccess();
        void onSuccess(List<MessageData> mlist);

        void onFailure();

        void onFailure(String msg);

    }
}
