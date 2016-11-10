package com.grupio.message;

import android.content.Context;

import com.grupio.dao.MessageDAO;
import com.grupio.data.MessageData;
import com.grupio.message.apis.APICallBack;
import com.grupio.message.apis.InboxAPI;
import com.grupio.message.apis.LoadMoreMsgAPI;
import com.grupio.message.apis.MarkMsgAsReadAPI;
import com.grupio.message.apis.MessageCountAPI;
import com.grupio.message.apis.SendMsgAPI;
import com.grupio.message.apis.SentMsgAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 6/7/16.
 */
public class MessageController implements ControllerInter {

    Context mContext;
    private List<MessageData> mList;
    private boolean isInbox = true;

    public MessageController(Context mContext) {
        this.mContext = mContext;
    }

    public void isFromInbox(boolean isInbox) {
        this.isInbox = isInbox;
    }

    @Override
    public void fetchInboxMsgs(final OnTaskComplete mListener) {


        InboxAPI mInboxAPI = new InboxAPI(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                if (isInbox) {
                    fetchInboxMsgsFromDb(null, mListener);
                }
            }

            @Override
            public void onFailure(String msg) {
                mListener.onFailure(msg);
            }
        });
        mInboxAPI.doCall("0");
    }


    @Override
    public void fetchSentMsgs(final OnTaskComplete mListener) {

        SentMsgAPI msgAPI = new SentMsgAPI(mContext);
        msgAPI.doCall(new APICallBack() {
            @Override
            public void onSuccess() {
                if (isInbox) {
                } else {
                    fetchSentMsgsFromDb(null, mListener);
                }
            }

            @Override
            public void onFailure(String msg) {
                mListener.onFailure(msg);
            }
        });
    }

    @Override
    public void loadMoreMsgs(final OnTaskComplete mListener) {

        LoadMoreMsgAPI moreMsgAPI = new LoadMoreMsgAPI(mContext);
        moreMsgAPI.doCall(new APICallBack() {
            @Override
            public void onSuccess() {
                if (isInbox) {
                    fetchInboxMsgsFromDb(null, mListener);
                } else {
                    fetchSentMsgsFromDb(null, mListener);
                }
            }

            @Override
            public void onFailure(String msg) {
                mListener.onFailure(msg);
            }
        });
    }

    @Override
    public void fetchMessageCount(final OnTaskComplete mListener) {

        MessageCountAPI messageCountAPI = new MessageCountAPI(mContext);
        messageCountAPI.doCall(new APICallBack() {
            @Override
            public void onSuccess() {
                mListener.showCount();
            }

            @Override
            public void onFailure(String msg) {
                mListener.hideCount();
            }
        });
    }

    @Override
    public void fetchInboxMsgsFromDb(String query, OnTaskComplete mListener) {

        mList = new ArrayList<>();
        mList.addAll(MessageDAO.getInstance(mContext).getInboxMsgList(query));

        if (mList.size() > 0) {
            mListener.onSuccess(mList);
        } else {
            mListener.onFailure();
        }

    }

    @Override
    public void fetchSentMsgsFromDb(String query, OnTaskComplete mListener) {
        mList = new ArrayList<>();
        mList.addAll(MessageDAO.getInstance(mContext).getSentMsgList(query));
        if (mList.size() > 0) {
            mListener.onSuccess(mList);
        } else {
            mListener.onFailure();
        }
    }

    @Override
    public void fetchMessageListOfParticularThreadFromDb(String threadId, OnTaskComplete mListener) {
        mList = new ArrayList<>();
        mList.addAll(MessageDAO.getInstance(mContext).getMessageListOfParticularList(threadId));
        mListener.onSuccess(mList);

        String unreadMessages = MessageDAO.getInstance(mContext).getUnreadMsgIds(threadId);

        if (!unreadMessages.equals("")) {
            markMessageRead(unreadMessages, threadId);
        }
    }

    @Override
    public void sendMessage(final MessageData msg, final OnTaskComplete mListener) {

        SendMsgAPI msgAPI = new SendMsgAPI(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                if (!isInbox) {
                    MessageCountWatcher.getInstance().updateSentList();
                }

                fetchMessageListOfParticularThreadFromDb(msg.getThread_id(), mListener);
            }

            @Override
            public void onFailure(String msg) {
                mListener.onFailure(msg);
            }
        });
        msgAPI.doCall(msg);

    }

    //Mark All Unread Messge of this thread as Read
    @Override
    public void markMessageRead(String messageId, String threadId) {
        MarkMsgAsReadAPI markMsgAsReadAPI = new MarkMsgAsReadAPI(mContext);
        markMsgAsReadAPI.doCall(messageId, threadId);
    }

    @Override
    public void clearData() {
        MessageDAO.getInstance(mContext).deleteMsgs();
    }


}
