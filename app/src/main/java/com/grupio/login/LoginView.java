package com.grupio.login;

import android.os.Bundle;

/**
 * Created by JSN on 13/10/16.
 */

public interface LoginView {

    void showProgress();

    void hideProgress();

    void userNameError(String msg);

    void passwordError(String msg);

    void loginFailed(String msg);

    void loginSuccess();

    void goToMessageMenu();

    void goToListDetailActivity();

    void goToGridHome();

    void goToDiscussionBoard();

    void goToNotesActivity();

    void downloadDocument();

    void navigateScreen(Bundle mbundle, Class<?> className);

    void goToMyNotesScreen();
}
