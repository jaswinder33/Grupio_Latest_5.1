package com.grupio.attendee.message;

import android.content.Context;

import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by JSN on 18/10/16.
 */

public interface MessageInteractorImp {
    void sendMessage(String subject, String message, List<AttendeesData> sendTo, Context mContext, onInteractionComplete mListener);

    void fetchAttendeeName(String attendeeId, Context mContext, onInteractionComplete mListener);

    interface onInteractionComplete {
        void onAttendeeNameFetch(String name, List<AttendeesData> mData);

        void onMessageSent();

        void onFailure(String message);
    }

}
