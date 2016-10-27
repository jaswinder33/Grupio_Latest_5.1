package com.grupio.eventlist;

import com.grupio.data.EventData;

import java.util.List;

/**
 * Created by JSN on 5/9/16.
 */
public interface EventControllerInter {

    void fetchEventListFromServer(String query, onEventController mListener);

    interface onEventController {
        void onEventListFetch(List<EventData> mList);

        void onFailure(String msg);
    }
}
