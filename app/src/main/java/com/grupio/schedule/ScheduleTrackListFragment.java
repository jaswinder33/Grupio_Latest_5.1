package com.grupio.schedule;

import android.os.Bundle;

import com.grupio.data.ScheduleData;
import com.grupio.fragments.BaseFragment;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class ScheduleTrackListFragment extends BaseFragment<ScheduleTrackListPresenter> implements ScheduleTrackListContract.ScheduleView {
    ScheduleTrackListFragment() {
    }

    public static ScheduleTrackListFragment getInstance(Bundle mbundle) {
        ScheduleTrackListFragment mFrag = new ScheduleTrackListFragment();
        if (mbundle != null) {
            mFrag.setArguments(mbundle);
        }
        return mFrag;
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
    public ScheduleTrackListPresenter setPresenter() {
        return null;
    }

    @Override
    public void initIds() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void showLis(List<ScheduleData> mList) {

    }
}
