package com.grupio.login;

import android.content.Context;

/**
 * Created by JSN on 13/10/16.
 */

public interface LoginPresenterImp {


    void doLogin(Context mcontext, String usreName, String password);

    void goToNextScreen(String screenName);


}
