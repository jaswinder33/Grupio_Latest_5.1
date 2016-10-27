package com.grupio.apis;

import android.content.Context;

import com.grupio.R;

/**
 * Created by JSN on 22/8/16.
 */
public abstract class BaseApiCall implements Runnable, ApiInter {

    protected Context mContext;
    protected ApiCallBack mListerner;
    protected String url;

    public BaseApiCall(){}

    public BaseApiCall(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void netNotAvailable(ApiCallBack mListener) {
        if (mListerner != null) {
            mListerner.onFailure("Internet not available");
        }
    }

    @Override
    public void run() {
        prepareUrl();
        callApi();
    }

    public void prepareUrl() {
        String baseURL = mContext.getResources().getString(R.string.base_url);
        String endPoint = getEndPoint();
        String apiFormat = mContext.getResources().getString(R.string.api_format);
        url = baseURL + endPoint + apiFormat;

    }

    public abstract String getEndPoint();

    public abstract void callApi();

}
