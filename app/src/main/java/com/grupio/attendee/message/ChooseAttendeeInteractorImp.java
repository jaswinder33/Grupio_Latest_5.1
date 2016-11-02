package com.grupio.attendee.message;

import android.content.Context;

import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by JSN on 19/10/16.
 */

public interface ChooseAttendeeInteractorImp {

    void fetchAttendeeList(Context mContext, String queryStr, String cateogory, onAttendeeInteraction mListener);

    void refreshAttendeeList(Context mContext, onAttendeeInteraction mListener);

    interface onAttendeeInteraction {
        void onFailure(String msg);

        void onAttendeeListFetch(List<AttendeesData> mList);
    }
}
