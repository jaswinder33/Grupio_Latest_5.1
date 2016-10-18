package com.grupio.apis;

import android.content.Context;

/**
 * Created by JSN on 19/8/16.
 */

/**
 * This is generic class to hit apis. All api call will go through this class.
 *
 * @param <T>
 */
public class APICall<T extends ApiInter> {

    private T type;

    public APICall(T type) {
        this.type = type;
    }

    public void doCall(Context mContext) {
        doCall(mContext, null);
    }

    public void doCall(Context mContext, ApiCallBack mListener) {
//        if (Utils.haveNetwork(mContext)) {
//            if (mListener == null) {
////                type.doCall(mContext);
//            } else {
////                type.doCall(mContext, mListener);
//            }
//        } else {
//            type.netNotAvailable(mListener);
//        }
    }

}
