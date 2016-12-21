package com.grupio.login;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.attendee.ListConstant;
import com.grupio.notes.NotesListActivity;

/**
 * Created by JSN on 13/10/16.
 */

public class LoginPresenter implements LoginPresenterImp, LoginInteractorImp.OnLoginComplete {

    private LoginView mListener;
    private LoginInteractor mInteractor;

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
            return false;
        }
        return true;
    }

    public boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            mListener.hideProgress();
            mListener.passwordError("password required");
            return false;
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

            case ListConstant.SESSION:
                mListener.goToNotesActivity();
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
                mListener.goToMyAccountScreen();
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

            case NotesListActivity.My_NOTES:
            case NotesListActivity.THINGS_TO_DO:
                mListener.goToMyNotesScreen();
                break;

            case "photo_gallery":
                break;
            case ListConstant.BEST_Match:
                mListener.goToListActivity(ListConstant.BEST_Match);
                break;

            case "chat":
                break;

            case "game":
                break;

            case "ListDetailActivity":
                mListener.goToListDetailActivity();
                break;

            case "sessionDoc":
                mListener.downloadDocument();
                break;

            case "new_meeting":
                mListener.goToNewMeeting();
                break;

        }
    }
}
