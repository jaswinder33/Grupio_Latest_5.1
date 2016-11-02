package com.grupio.login;

import android.content.Context;

/**
 * Created by mani on 13/10/16.
 */

public interface LoginInteractorImp {

    void doLogin(Context mcontext, String usreName, String password, OnLoginComplete mListener);

    interface OnLoginComplete {
        void onLoginFailed(String msg);

        void onSuccess();
    }
}
