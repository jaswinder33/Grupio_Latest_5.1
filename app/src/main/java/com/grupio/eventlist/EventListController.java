package com.grupio.eventlist;

import android.content.Context;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.apis.EventListAPI;
import com.grupio.data.EventData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by JSN on 5/9/16.
 */
public class EventListController implements EventControllerInter {

    private Context mContext;
    public EventListController(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void fetchEventListFromServer(String query, onEventController mListener) {

        if (Utility.hasInternet(mContext)) {
            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<List<EventData>> task = es.submit(new EventListAPI(mContext, query));

            try {
                mListener.onEventListFetch(task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            mListener.onFailure(mContext.getResources().getText(R.string.no_internet).toString());
        }

    }


}
