package com.grupio.eventlist;

import com.grupio.data.EventData;

import java.util.List;

/**
 * Created by JSN on 5/9/16.
 */
public interface EventControllerInter {

    interface onEventController {
        void onEventListFetch(List<EventData> mList);
    }

    void fetchEventListFromServer(String query, onEventController mListener);
}
