package com.grupio.message;

import android.content.Context;

import com.grupio.data.MessageData;

import java.util.List;

/**
 * Created by JSN on 11/7/16.
 */
public class MsgDetailPresenter implements MsgDetailPres, ControllerInter.OnTaskComplete {

    private MsgDetailView mListener;
    private MessageController messageController;
    private boolean isInbox;

    public MsgDetailPresenter(MsgDetailView mListener, Context mContext, String threadId, boolean isInbox) {
        this.isInbox = isInbox;
        this.mListener = mListener;
        messageController = new MessageController(mContext);
        messageController.isFromInbox(isInbox);
//        fetchMessageOfThisThread(threadId);
    }


    @Override
    public void fetchMessageOfThisThread(String id) {
        messageController.fetchMessageListOfParticularThreadFromDb(id, this);
    }

    @Override
    public void sendMessage(MessageData msg) {
        if (mListener != null) {
            mListener.showProgress();
        }
        messageController.sendMessage(msg, this);
    }

    @Override
    public void showMsgList(List<MessageData> mList) {
        if (mListener != null) {
            mListener.showMsgList(mList);
        }
    }

    @Override
    public void markThisMsgRead(String msgId, String theadId) {
        messageController.markMessageRead(msgId, theadId);
    }


    //    Do not use this function
    @Override
    public void showCount() {

    }

    //    Do not use this function
    @Override
    public void hideCount() {

    }

    @Override
    public void onSuccess(List<MessageData> mlist) {
        if (mListener != null) {
            mListener.showMsgList(mlist);
            mListener.hideProgress();
        }
    }

    @Override
    public void onFailure() {
//        if (mListener != null) {
//            mListener.failure();
//            mListener.hideProgress();
//        }
    }

    @Override
    public void onFailure(String msg) {
        if (mListener != null) {
            mListener.failure(msg);
            mListener.hideProgress();
        }
    }
}
