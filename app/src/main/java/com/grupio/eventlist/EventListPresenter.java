package com.grupio.eventlist;

import android.content.Context;

import com.grupio.data.EventData;

import java.util.List;


/**
 * Created by JSN on 5/9/16.
 */
public class EventListPresenter implements EventListPresenterImp, EventControllerInter.onEventController {

    private EventListView mListener;
    private EventListController mController;

    public EventListPresenter(Context mcontext, EventListView mListener) {
        this.mListener = mListener;
        mController = new EventListController(mcontext);
        fetchEventListFromServer("");
    }


    @Override
    public void fetchEventListFromServer(final String query) {
        if (mListener != null) {
            mListener.showProgess();

            Runnable runnable = new Runnable() {
                public void run() {
                    mController.fetchEventListFromServer(query, EventListPresenter.this);
                }
            };
            new Thread(runnable).start();
        }
    }

    @Override
    public void onEventListFetch(List<EventData> mList) {
        if (mListener != null) {
            mListener.hideProgress();
            mListener.showEventList(mList);
        }
    }

    @Override
    public void onFailure(String msg) {
        if (mListener != null) {
            mListener.hideProgress();
            mListener.onFailure(msg);
        }
    }
}
