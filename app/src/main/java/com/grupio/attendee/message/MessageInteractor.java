package com.grupio.attendee.message;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.dao.AttendeeDAO;
import com.grupio.data.AttendeesData;
import com.grupio.data.MessageData;
import com.grupio.message.apis.APICallBack;
import com.grupio.message.apis.SendMsgAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 18/10/16.
 */

public class MessageInteractor implements MessageInteractorImp {
    @Override
    public void sendMessage(String subject, String message, List<AttendeesData> sendTo, Context mContext, onInteractionComplete mListener) {

        if (isValidMessage(message, mListener) && isValidSubject(subject, mListener)) {
            sendMessageToServer(subject, message, sendTo, mContext, mListener);
        }
    }

    @Override
    public void fetchAttendeeName(String attendeeId, Context mContext, onInteractionComplete mListener) {
        List<String> attendeeIdlist = new ArrayList<>();
        attendeeIdlist.add(attendeeId);

        List<AttendeesData> mList = new ArrayList<>();
        mList.addAll(AttendeeDAO.getInstance(mContext).fetchAttendeesOfParticularids(attendeeIdlist));

        String name = "";
        for (int i = 0; i < mList.size(); i++) {
            name += mList.get(i).getFirst_name() + " " + mList.get(i).getLast_name() + ",";
        }
        mListener.onAttendeeNameFetch(name, mList);
    }

    public boolean isValidSubject(String subject, onInteractionComplete mListener) {
        if (TextUtils.isEmpty(subject)) {
            mListener.onFailure("Empty Subject");
            return false;
        }
        return true;
    }

    public boolean isValidMessage(String message, onInteractionComplete mListener) {
        if (TextUtils.isEmpty(message)) {
            mListener.onFailure("Empty message");
            return false;
        }
        return true;
    }

    public void sendMessageToServer(String subject, String message, List<AttendeesData> sendTo, Context mContext, onInteractionComplete mListener) {

        String receiverList = sendTo.get(0).getAttendee_id();
        for (int i = 1; i < sendTo.size(); i++) {
            receiverList = receiverList + "," + sendTo.get(i).getAttendee_id();
        }

        MessageData msg = new MessageData();
        msg.setTitle(subject);
        msg.setContent(message);
        msg.setReceiver_id(receiverList);
        msg.setThread_id("");


        SendMsgAPI msgAPI = new SendMsgAPI(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                mListener.onMessageSent();
            }

            @Override
            public void onFailure(String msg) {
                mListener.onFailure(msg);
            }
        });
        msgAPI.doCall(msg);

    }

}
