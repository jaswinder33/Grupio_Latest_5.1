package com.grupio.message;


import com.grupio.data.MessageData;

import java.util.List;

/**
 * Created by JSN on 11/7/16.
 */
public interface MsgDetailView {
    void showMsgList(List<MessageData> mList);

    void failure(String msg);

    void showProgress();

    void hideProgress();

}
