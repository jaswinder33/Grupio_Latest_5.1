package com.grupio.login;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by JSN on 13/10/16.
 */

public class LoginPresenter implements LoginPresenterImp, LoginInteractorImp.OnLoginComplete {

    LoginView mListener;
    LoginInteractor mInteractor;

    public LoginPresenter(LoginView mListener) {
        this.mListener = mListener;
        mInteractor = new LoginInteractor();
    }

    @Override
    public void doLogin(Context mcontext, String userName, String password) {
        mListener.showProgress();
        if (checkUserName(userName) && checkPassword(password)) {
            mInteractor.doLogin(mcontext, userName, password, this);
        }
    }

    @Override
    public void goToNextScreen(String screenName) {
        screenNavigate(screenName);
    }

    public boolean checkUserName(String userName) {
        if (TextUtils.isEmpty(userName)) {
            mListener.hideProgress();
            mListener.userNameError("Username required");
        }
        return true;
    }

    public boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            mListener.hideProgress();
            mListener.passwordError("password required");
        }

        return true;
    }

    @Override
    public void onLoginFailed(String msg) {
        mListener.hideProgress();
        mListener.loginFailed(msg);
    }

    @Override
    public void onSuccess() {
        mListener.hideProgress();
        mListener.loginSuccess();
    }

    public void screenNavigate(String name) {
        System.out.print("ScreenNavigateMethod called: " + name);
        switch (name) {

            case "activity_feed":
                //Leave pending
                break;

            case "schedule":
                break;

            case "mycalendar":
                break;

            case "speakers":
                break;

            case "exhibitors":
                break;

            case "sponsors":
                break;

            case "maps":
                break;


            case "live":
                break;

            case "social":
                break;

            case "logistics":
                break;

            case "logistics2":
                break;

            case "logistics3":
                break;
            case "my account":
                break;

            case "survey":
                break;

            case "attendees":
                break;

            case "matches":
                break;

            case "messages":
                mListener.goToMessageMenu();
                break;

            case "Alerts":
                break;

            case "downloads":
                break;

            case "qrcode":
                break;

            case "search":
                break;

            case "discussion_board":
                mListener.goToDiscussionBoard();
                break;

            case "my_notes":
                break;

            case "things_to_do":
                break;
            case "photo_gallery":
                break;
            case "i2i":
                break;

            case "chat":
                break;

            case "game":
                break;

            case "ListDetailActivity":
                mListener.goToListDetailActivity();
                break;

        }
    }
}