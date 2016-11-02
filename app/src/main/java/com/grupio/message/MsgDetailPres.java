package com.grupio.message;


import com.grupio.data.MessageData;

import java.util.List;

/**
 * Created by JSN on 11/7/16.
 */
public interface MsgDetailPres {
    void fetchMessageOfThisThread(String id);

    void sendMessage(MessageData msg);

    void showMsgList(List<MessageData> mList);

    void markThisMsgRead(String msgId, String theadId);
}
