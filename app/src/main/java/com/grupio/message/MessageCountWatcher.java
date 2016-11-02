package com.grupio.message;

/**
 * Created by JSN on 15/7/16.
 */
public class MessageCountWatcher {

    private static MessageCountWatcher mMessageCountWatcher;
    private MessageListFragInter mListener;


    private MessageCountWatcher() {
    }

    public static MessageCountWatcher getInstance() {

        if (mMessageCountWatcher == null) {
            mMessageCountWatcher = new MessageCountWatcher();
        }
        return mMessageCountWatcher;
    }

    public void register(MessageListFragInter mListener) {
        this.mListener = mListener;
    }

    public void updateCount() {
        if (mListener != null) {
            mListener.showMessageCount();
        }
        notifyAdapter();
    }

    public void notifyAdapter() {
        if (mListener != null) {
            mListener.notifyAdapter();
        }
    }

    public void updateSentList() {
        if (mListener != null) {
            mListener.sentMessageList();
        }
    }

}
