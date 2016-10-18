package com.grupio.apis;

import android.content.Context;

/**
 * Created by JSN on 22/8/16.
 */
public class BaseApiCall implements Runnable, ApiInter {

    protected Context mContext;
    protected ApiCallBack mListerner;

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

    }


}
