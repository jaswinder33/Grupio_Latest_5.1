package com.grupio.attendee.message;

import android.content.Context;

import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by JSN on 18/10/16.
 */

public interface MessagePresenterImp {
    void fetchAttendeeName(String attendeeId, Context mContext);

    void sendMessage(String subject, String message, List<AttendeesData> sendTo, Context mContext);
}
