package com.grupio.apis;


import android.content.Context;

import com.grupio.R;

import java.util.concurrent.Callable;

/**
 * Created by mani on 13/10/16.
 */

public abstract class BaseCallable<Result> implements Callable<Result>, ApiInter {

    protected Context mContext;
    protected String url;


    public BaseCallable(Context mContext) {
        this.mContext = mContext;
    }

    public abstract String getEndPoint();

    public abstract Result callApi();

    @Override
    public Result call() throws Exception {
        prepareUrl();
        return callApi();
    }

    public void prepareUrl() {
        String baseURL = mContext.getResources().getString(R.string.base_url);
        String endPoint = getEndPoint();
        String apiFormat = mContext.getResources().getString(R.string.api_format);
        url = baseURL + endPoint + apiFormat;

    }

    @Override
    public void netNotAvailable(ApiCallBack mListener) {

    }


}
