package com.grupio.services;

import android.content.Context;

import com.grupio.message.apis.APICallBack;

/**
 * Created by JSN on 17/10/16.
 */

public interface ServiceContract<T> {
    void sendMessage(T t, Context mContext, APICallBack mListener);
}
