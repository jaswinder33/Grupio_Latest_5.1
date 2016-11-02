package com.grupio.login;

import android.content.Context;

import com.grupio.apis.LoginApi;
import com.grupio.message.apis.APICallBack;

/**
 * Created by JSN on 13/10/16.
 */

public class LoginInteractor implements LoginInteractorImp {


    @Override
    public void doLogin(Context mcontext, String usreName, String password, OnLoginComplete mListener) {

        LoginApi api = new LoginApi(mcontext, new APICallBack() {
            @Override
            public void onSuccess() {
                mListener.onSuccess();
            }

            @Override
            public void onFailure(String msg) {
                mListener.onLoginFailed(msg);
            }
        });
        api.doCall(usreName, password);

    }


}
