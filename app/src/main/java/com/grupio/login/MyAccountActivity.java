package com.grupio.login;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.interfaces.ClickHandler;
import com.grupio.session.Preferences;

/**
 * Created by JSN on 30/11/16.
 */

public class MyAccountActivity extends BaseActivity<Void> {
    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void initIds() {
    }

    @Override
    public boolean isHeaderForGridPage() {
        return true;
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
    public Void setPresenter() {
        return null;
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void setUp() {
        handleRightBtn(true, LOGOUT);
        Utility.replaceFragment(this, new MyAccountFragment(), false);
    }

    @Override
    public void handleRightBtnClick() {

        ClickHandler logoutClick = () -> {
            Preferences.getInstances(this).setAttendeeId(null);
            Preferences.getInstances(this).saveUserInfo(null);
            onBackPressed();
        };
        CustomDialog.getDialog(this, logoutClick).show(getString(R.string.logout_permission));


    }
}
