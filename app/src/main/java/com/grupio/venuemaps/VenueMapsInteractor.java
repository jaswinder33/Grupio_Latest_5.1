package com.grupio.venuemaps;

import android.app.Activity;
import android.content.Context;

import com.grupio.R;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.AlertsAPI;
import com.grupio.apis.LiveAPI;
import com.grupio.apis.MapsAPI;
import com.grupio.apis.MarkAlertRead;
import com.grupio.apis.SurveyAPI;
import com.grupio.dao.LiveDAO;
import com.grupio.dao.MapsDAO;
import com.grupio.dao.SurveyDAO;
import com.grupio.data.AlertData;
import com.grupio.data.LiveData;
import com.grupio.data.MapsData;
import com.grupio.data.SurveyData;
import com.grupio.helper.AlertHelper;
import com.grupio.message.apis.APICallBack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by JSN on 24/10/16.
 */

public class VenueMapsInteractor implements VenueMapContract.MapsInteractor {

    @Override
    public void markAlertRead(String alertId, Context mContext, VenueMapContract.OnInteraction mlistener) {

        new MarkAlertRead(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                mlistener.onMessageMarkedRead(alertId);
            }

            @Override
            public void onFailure(String msg) {
                mlistener.onFailure(mContext.getString(R.string.erroor_occured));
            }
        }).doCall(alertId);
    }

    @Override
    public <T> void fetchListFromServer(T t, Context mContext, VenueMapContract.OnInteraction mlistener) {
        if (t instanceof MapsData) {
            fetchMapsFromServer(mContext, mlistener);
        } else if (t instanceof LiveData) {
            fetchLiveFeedsFromServer(mContext, mlistener);
        } else if (t instanceof AlertData) {
            fetchAlertsFromServer(mContext, mlistener);
        } else if (t instanceof SurveyData) {
            fetchSurveyFromServer(mContext, mlistener);
        }
    }

    @Override
    public <T> void fetchList(T t, Context mContext, VenueMapContract.OnInteraction mlistener) {
        if (t instanceof MapsData) {
            fetchVenuMapsFromDb(mContext, mlistener);
        } else if (t instanceof LiveData) {
            fetchLiveFeedFromDb(mContext, mlistener);
        } else if (t instanceof AlertData) {
            fetchAlertsFromServer(mContext, mlistener);
        } else if (t instanceof SurveyData) {
            fetchSurveyFromDb(mContext, mlistener);
        }
    }

    private void fetchAlertsFromServer(Context mContext, VenueMapContract.OnInteraction mlistener) {
        new AlertsAPI(mContext, new APICallBackWithResponse() {
            @Override
            public void onSuccess(String response) {
                AlertHelper alertHelper = new AlertHelper();
                mlistener.onListFetch(alertHelper.parseList(response));
            }

            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String msg) {
                mlistener.onFailure(msg);
            }
        }).doCall();
    }

    private void fetchLiveFeedsFromServer(Context mContext, VenueMapContract.OnInteraction mlistener) {
        new LiveAPI(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                mlistener.onListFetch(LiveDAO.getInstance(mContext).getData());
            }

            @Override
            public void onFailure(String msg) {
                mlistener.onFailure(msg);
            }
        }).doCall();
    }

    private void fetchMapsFromServer(Context mContext, VenueMapContract.OnInteraction mlistener) {

        new Thread(() -> {

            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(new MapsAPI(mContext));

            es.shutdown();
            try {
                es.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ((Activity) mContext).runOnUiThread(() -> {
                mlistener.onListFetch(MapsDAO.getInstance(mContext).getMapList());
            });

        }).start();

    }

    private void fetchSurveyFromServer(Context mContext, VenueMapContract.OnInteraction mlistener) {

        new Thread(() -> {

            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(new SurveyAPI(mContext));

            es.shutdown();
            try {
                es.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ((Activity) mContext).runOnUiThread(() -> {
                mlistener.onListFetch(SurveyDAO.getInstance(mContext).getData());
            });

        }).start();


    }

    private void fetchVenuMapsFromDb(Context mContext, VenueMapContract.OnInteraction mlistener) {
        mlistener.onListFetch(MapsDAO.getInstance(mContext).getMapList());
    }

    private void fetchLiveFeedFromDb(Context mContext, VenueMapContract.OnInteraction mlistener) {
        mlistener.onListFetch(LiveDAO.getInstance(mContext).getData());
    }

    private void fetchSurveyFromDb(Context mContext, VenueMapContract.OnInteraction mlistener) {
        mlistener.onListFetch(SurveyDAO.getInstance(mContext).getData());
    }
}
