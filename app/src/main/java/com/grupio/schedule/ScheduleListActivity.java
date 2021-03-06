package com.grupio.schedule;


import android.os.Bundle;
import android.view.View;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.attendee.ListWatcher;
import com.grupio.backend.Permissions;

public class ScheduleListActivity extends BaseActivity<Void> {

    String trackid = null;

    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void initIds() {

    }

    @Override
    public boolean isHeaderForGridPage() {
        return false;
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
        getData();
        Bundle mBundle = new Bundle();
        mBundle.putString("trackId", trackid);
        Utility.addFragment(this, ScheduleListFragment.getInstance(mBundle), false);

        Permissions.getInstance().hasCalendarPermission(this).askForPermissions(this, CALENDAR_PERMISSION);
    }

    @Override
    public void handleRightBtnClick(View view) {
    }

    public void getData() {
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {
            trackid = mBundle.getString("track");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ListWatcher.getInstance().unregisterListener();
    }
}
