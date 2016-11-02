package com.grupio.services;

import android.content.Context;

/**
 * Created by JSN on 17/10/16.
 */

public class Service {

    private ServiceContract serviceContract;

    public Service(ServiceContract serviceContract) {
        this.serviceContract = serviceContract;
    }

    public void sendMessage(String message) {
        serviceContract.sendMessage(message);
    }

    public void sendMessage(String emailAddress, Context mContext) {
        serviceContract.sendMessage(emailAddress, mContext);
    }

}
