package com.grupio.apis;

import com.grupio.message.apis.APICallBack;

/**
 * Created by JSN on 2/11/16.
 */

public interface APICallBackWithResponse extends APICallBack {
    void onSuccess(String response);
}
