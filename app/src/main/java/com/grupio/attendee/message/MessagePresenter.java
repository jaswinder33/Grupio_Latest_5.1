package com.grupio.attendee.message;

import android.content.Context;

import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by mani on 18/10/16.
 */

public class MessagePresenter implements MessagePresenterImp, MessageInteractorImp.onInteractionComplete {

    private MessageView mListener;
    private MessageInteractor mInteractor;

    public MessagePresenter(MessageView mListener) {
        this.mListener = mListener;
        mInteractor = new MessageInteractor();
    }

    @Override
    public void fetchAttendeeName(String attendeeId, Context mContext) {
        mInteractor.fetchAttendeeName(attendeeId, mContext, this);
    }

    @Override
    public void sendMessage(String subject, String message, List<AttendeesData> sendTo, Context mContext) {
        mListener.showProgress();
        mInteractor.sendMessage(subject, message, sendTo, mContext, this);
    }


    @Override
    public void onAttendeeNameFetch(String name, List<AttendeesData> mData) {
        mListener.setAttendeeName(name, mData);
    }

    @Override
    public void onMessageSent() {
        mListener.hideProgress();
        mListener.onSuccess();
    }

    @Override
    public void onFailure(String message) {
        mListener.hideProgress();
        mListener.onFailure(message);
    }
}
