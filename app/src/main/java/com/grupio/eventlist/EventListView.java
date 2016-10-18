package com.grupio.eventlist;

import android.view.View;

import com.grupio.data.EventData;

import java.util.List;


/**
 * Created by JSN on 5/9/16.
 */
public interface EventListView {

    void showProgess();

    void hideProgress();

    void showEventList(List<EventData> mlist);

    void hideKeyboard(View view);

    void onSearchError(String errorMsg);

}
