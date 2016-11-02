package com.grupio.message;

/**
 * Created by JSN on 6/7/16.
 */
public interface PresenterInter {

    void fetchInboxMsgs(String query);

    void fetchSentMsgs(String query);

    void loadMoreMsgs(boolean isInbox);

    void fetchMessageCount();

}
