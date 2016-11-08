package com.grupio.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.animation.SlideOut;
import com.grupio.data.TrackData;
import com.grupio.fragments.BaseFragment;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class ScheduleTrackListFragment extends BaseFragment<ScheduleTrackListPresenter> implements ScheduleTrackListContract.ScheduleView {

    AdapterView.OnItemClickListener mListner = (adapterView, view1, i, l) -> {
        Intent mIntent = new Intent();
        mIntent.setClass(getActivity(), ScheduleListActivity.class);
        mIntent.putExtra("track", ((TrackData) adapterView.getItemAtPosition(i)).id);
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(getActivity());
    };
    private ListView mListview;
    private TextView noDataAvailable;

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
        return "SCHEDULE_TRACK_VIEW";
    }

    @Override
    public String getBannerName() {
        return "schedule";
    }

    @Override
    public ScheduleTrackListPresenter setPresenter() {
        return new ScheduleTrackListPresenter(this);
    }

    @Override
    public void initIds() {
        mListview = (ListView) view.findViewById(R.id.logisticsList);
        noDataAvailable = (TextView) view.findViewById(R.id.noDataAvailable);
        mListview.setEmptyView(noDataAvailable);
    }

    @Override
    public void setListeners() {
        mListview.setOnItemClickListener(mListner);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void setUp() {
        getPresenter().fetchList(getActivity());
    }

    @Override
    public void showList(List<TrackData> mList) {
        TrackListAdapter tAdapter = new TrackListAdapter(getActivity());
        tAdapter.addAll(mList);
        mListview.setAdapter(tAdapter);
    }
}
