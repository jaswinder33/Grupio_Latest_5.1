package com.grupio.apis;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.message.apis.APICallBack;

/**
 * Created by JSN on 14/10/16.
 */

public abstract class BaseAsyncTask<Request, Response> extends AsyncTask<Request, Void, Response> {

    protected Context mContext;
    protected String url;
    protected APICallBack mListener;

    public BaseAsyncTask(Context mcontext, APICallBack mListener) {
        this.mContext = mcontext;
        this.mListener = mListener;
    }

    public void doCall(Request... params) {
        if (Utility.hasInternet(mContext)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                executeOnExecutor(THREAD_POOL_EXECUTOR, params);
            } else {
                execute(params);
            }
        } else {
            mListener.onFailure("You are offline");
        }
    }

    public void prepareUrl() {
        url = mContext.getString(R.string.base_url);
        url += endPoint();
    }

    public abstract String endPoint();

    @Override
    protected Response doInBackground(Request... params) {
        prepareUrl();
        return handleBackground(params);
    }

    public abstract Response handleBackground(Request... params);

    @Override
    protected void onPostExecute(Response mResponse) {
        super.onPostExecute(mResponse);
        if (mResponse instanceof Boolean) {
            if ((Boolean) mResponse) {
                mListener.onSuccess();
            } else {
                mListener.onFailure(null);
            }
        }
    }
}
