package com.grupio.services;

import android.content.Context;

/**
 * Created by JSN on 17/10/16.
 */

public interface ServiceContract {
    void sendMessage(String message);

    void sendMessage(String address, Context mContext);
}
