package com.grupio.calendar;

import android.view.View;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;

/**
 * Created by JSN on 12/12/16.
 */

public class CalendarActivity extends BaseActivity<Void> {

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
        Utility.replaceFragment(this, new CalendarFragment(), false);
    }

    @Override
    public void handleRightBtnClick(View view) {
        goToNextScreen(null, NewMeetingActivity.class);
    }
}
