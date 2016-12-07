package com.grupio.schedule;

import android.view.View;

import com.grupio.R;
import com.grupio.activities.BaseActivity;


public class ScheduleTrackListActivity extends BaseActivity {

    @Override
    public boolean isHeaderForGridPage() {
        return true;
    }

    @Override
    public void handleRightBtnClick(View view) {
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
    }

    @Override
    public void setListeners() {
    }

    @Override
    public Object setPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void setUp() {
        getFragmentManager().beginTransaction().add(R.id.container, ScheduleTrackListFragment.getInstance(null), ScheduleTrackListFragment.class.getSimpleName()).commit();
    }
}
