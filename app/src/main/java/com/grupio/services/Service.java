package com.grupio.services;

import android.content.Context;

import com.grupio.message.apis.APICallBack;

/**
 * Created by JSN on 17/10/16.
 */

public class Service<T> {

    private ServiceContract serviceContract;

    public Service(ServiceContract serviceContract) {
        this.serviceContract = serviceContract;
    }

    public void sendMessage(T data, Context mContext, APICallBack mListener) {
        serviceContract.sendMessage(data, mContext, mListener);
    }
}
