package com.grupio.apis;

/**
 * Created by JSN on 22/8/16.
 */
public interface ApiCallBack<T> {
    void onSuccess(T response);
    void onFailure(T msg);
}
