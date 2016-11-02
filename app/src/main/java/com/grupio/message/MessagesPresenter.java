package com.grupio.message;

import android.content.Context;

import com.grupio.data.MessageData;

import java.util.List;

/**
 * Created by JSN on 6/7/16.
 */
public class MessagesPresenter implements PresenterInter, ControllerInter.OnTaskComplete {

    private MessageListFragInter mListener;
    private MessageController mControllerInter;
    private Context mContext;

    public MessagesPresenter(Context mContext, MessageListFragInter mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
        mControllerInter = new MessageController(mContext);
        fetchDataFromServer(true);
    }

    public void fetchDataFromServer(boolean isInbox) {
        if (mListener != null) {
            mListener.showProgress();
        }
        mControllerInter.clearData();
        mControllerInter.isFromInbox(isInbox);
        mControllerInter.fetchInboxMsgs(this);
        mControllerInter.fetchSentMsgs(this);
        fetchMessageCount();
    }

    @Override
    public void showCount() {
        if (mListener != null) {
            mListener.showMessageCount();
        }
    }

    @Override
    public void hideCount() {
        if (mListener != null) {
            mListener.hideMessageCount();
        }
    }

    @Override
    public void onSuccess(List<MessageData> mlist) {
        if (mListener != null) {
            mListener.hideProgress();
            if (mlist == null) {
                mListener.showNoTxt();
            } else {
                mListener.showMessageList(mlist);
            }
        }
    }

    @Override
    public void onFailure() {
        if (mListener != null) {
            mListener.hideProgress();
            mListener.showNoTxt();
        }
    }

    @Override
    public void onFailure(String msg) {
        if (mListener != null) {
            mListener.hideProgress();
            if (msg != null) {
                mListener.onFailure(msg);
            }
            mListener.showNoTxt();
        }
    }

    @Override
    public void fetchInboxMsgs(String query) {
        if (mListener != null) {
            mControllerInter.fetchInboxMsgsFromDb(query, this);
        }
    }

    @Override
    public void fetchSentMsgs(String query) {
        if (mListener != null) {
            mControllerInter.fetchSentMsgsFromDb(query, this);
        }
    }

    @Override
    public void loadMoreMsgs(boolean isInbox) {
        if (mListener != null) {
            mListener.showProgress();
            mControllerInter.isFromInbox(isInbox);
            mControllerInter.loadMoreMsgs(this);
        }
    }

    @Override
    public void fetchMessageCount() {
        if (mListener != null) {
            mControllerInter.fetchMessageCount(this);
        }
    }


}
