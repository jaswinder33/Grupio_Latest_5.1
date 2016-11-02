package com.grupio.message;


import com.grupio.data.MessageData;

import java.util.List;

/**
 * Created by JSN on 6/7/16.
 */
public interface MessageListFragInter {

    void showProgress();

    void hideProgress();

    void showMessageList(List<MessageData> mlist);

    void showNoTxt();

    void showMessageCount();

    void hideMessageCount();

    void onFailure(String msg);

    void notifyAdapter();

    void sentMessageList();

}
