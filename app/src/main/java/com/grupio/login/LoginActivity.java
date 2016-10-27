package com.grupio.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.activities.WebViewActivity;
import com.grupio.animation.SlideIn;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.message.SendMessageActivity;
import com.grupio.message.MessageActivity;
import com.grupio.session.ConstantData;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    private TextView userName, userPassword;
    private Button submitBtn;
    private String menuFrom;
    private Boolean isForConnectBtn = false;
    private String attendeeId = "";
    private boolean isForMessage = false;

    @Override
    public boolean isHeaderForGridPage() {
        return true;
    }

    @Override
    public void handleRightBtnClick() {

    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }

    @Override
    public void initIds() {
        userName = (TextView) findViewById(R.id.userName);
        userPassword = (TextView) findViewById(R.id.userpassword);
        submitBtn = (Button) findViewById(R.id.submitBtn);
    }

    @Override
    public void setListeners() {
        submitBtn.setOnClickListener(this);
    }

    @Override
    public LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void setUp() {
        getData();
    }

    @Override
    public void showProgress() {
        showProgressDialog("Loggin in...");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void userNameError(String msg) {
        userName.setError(msg);
    }

    @Override
    public void passwordError(String msg) {
        userPassword.setError(msg);
    }

    @Override
    public void loginFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        getPresenter().goToNextScreen(menuFrom);
    }

    @Override
    public void goToMessageMenu() {
        Intent mIntent = new Intent(LoginActivity.this, MessageActivity.class);
        startActivity(mIntent);
        finish();
        SlideOut.getInstance().startAnimation(this);
    }

    @Override
    public void goToListDetailActivity() {
        if (isForConnectBtn) {
            Intent mIntent = new Intent();
            setResult(RESULT_OK, mIntent);
            finish();
            SlideIn.getInstance().startAnimation(this);
        } else if (isForMessage) {
            Intent mIntent = new Intent(this, SendMessageActivity.class);
            mIntent.putExtra("attendee_id", attendeeId);
            startActivity(mIntent);
            SlideOut.getInstance().startAnimation(this);
            finish();
        }
    }

    @Override
    public void goToGridHome() {
        finish();
        SlideIn.getInstance().startAnimation(this);
    }

    @Override
    public void goToDiscussionBoard() {
        String url = getString(R.string.base_url) + getString(R.string.discussion_board_api) + ConstantData.EVENT_ID;
        Bundle mBundle = new Bundle();
        mBundle.putString("url", url);
        navigateScreen(mBundle, WebViewActivity.class);
        SlideOut.getInstance().startAnimation(this);
    }

    @Override
    public void navigateScreen(Bundle mbundle, Class<?> className) {
        Intent mIntent = new Intent(this, className);
        mIntent.putExtras(mbundle);
        startActivity(mIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.submitBtn:
                getPresenter().doLogin(this, userName.getText().toString(), userPassword.getText().toString());
                break;
        }
    }

    public void getData() {
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {

            try {
                menuFrom = mBundle.getString("from");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                isForConnectBtn = mBundle.getBoolean("isForConnectBtn");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                isForMessage = mBundle.getBoolean("isForMessageBtn");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                attendeeId = mBundle.getString("attendee_id");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
