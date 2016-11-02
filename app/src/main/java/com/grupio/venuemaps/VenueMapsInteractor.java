package com.grupio.venuemaps;

import android.content.Context;

import com.grupio.R;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.AlertsAPI;
import com.grupio.apis.LiveAPI;
import com.grupio.apis.MarkAlertRead;
import com.grupio.dao.MapsDAO;
import com.grupio.data.AlertData;
import com.grupio.data.LiveData;
import com.grupio.data.MapsData;
import com.grupio.data.SurveyData;
import com.grupio.helper.AlertHelper;
import com.grupio.helper.LiveHelper;
import com.grupio.message.apis.APICallBack;

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
    public <T> void fetchList(T t, Context mContext, VenueMapContract.OnInteraction mlistener) {
        if (t instanceof MapsData) {
            mlistener.onListFetch(MapsDAO.getInstance(mContext).getMapList());
        } else if (t instanceof LiveData) {
            fetchLiveFeedsFromServer(mContext, mlistener);
        } else if (t instanceof AlertData) {
            fetchAlertsFromServer(mContext, mlistener);
        } else if (t instanceof SurveyData) {
            fetchSurveyFromDb(mContext, mlistener);
        }
    }

    public void fetchAlertsFromServer(Context mContext, VenueMapContract.OnInteraction mlistener) {
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

    public void fetchLiveFeedsFromServer(Context mContext, VenueMapContract.OnInteraction mlistener) {
        new LiveAPI(mContext, new APICallBackWithResponse() {
            @Override
            public void onSuccess(String response) {
                LiveHelper liveHelper = new LiveHelper();
                mlistener.onListFetch(liveHelper.parseList(mContext, response));
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

    public void fetchSurveyFromDb(Context mContext, VenueMapContract.OnInteraction mlistener) {

    }
}
