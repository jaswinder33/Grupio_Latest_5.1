package com.grupio.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.ListDetailActivity;
import com.grupio.attendee.ListWatcher;
import com.grupio.data.ScheduleData;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.ClickHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by JSN on 7/11/16.
 */

public class ScheduleListFragment extends BaseFragment<ScheduleListPresenter> implements ScheduleListContract.View, ListWatcher.Watcher {

    private static ScheduleListFragment mScheduleListFragment;


    AdapterView.OnItemClickListener mListClick = (adapterView, view1, i, l) -> {

        ScheduleData mScheduleData = (ScheduleData) adapterView.getAdapter().getItem(i);

        Intent mIntent = new Intent();
        mIntent.setClass(getActivity(), ListDetailActivity.class);
        mIntent.setType(ListDetailActivity.SESSIONS);
        mIntent.putExtra("id", mScheduleData.getSession_id());
        mIntent.putExtra("data", mScheduleData);
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(getActivity());
    };
    private TextView noDataAvailableTxt;
    private StickyListHeadersListView mListView;
    private List<String> mdateList;
    private int counter = 0;
    private String trackid = null;
    public TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            if (s.toString().length() > 0) {
                getPresenter().fetchList(trackid, s.toString(), mdateList.get(counter), getActivity());
            } else {
                getPresenter().fetchList(trackid, null, mdateList.get(counter), getActivity());
            }
        }
    };
    ClickHandler mLeftClick = () -> {
        if (counter > 0) {
            getPresenter().fetchList(trackid, searchEditTxt.getText().toString(), mdateList.get(counter - 1), getActivity());
            setDate(mdateList.get(counter - 1));
            counter--;
        }
    };
    ClickHandler mRightClick = () -> {
        if (counter < mdateList.size() - 1) {
            getPresenter().fetchList(trackid, searchEditTxt.getText().toString(), mdateList.get(counter + 1), getActivity());
            setDate(mdateList.get(counter + 1));
            counter++;
        }
    };


    public static ScheduleListFragment getInstance(Bundle mBundle) {
        mScheduleListFragment = new ScheduleListFragment();
        if (mBundle != null) {
            mScheduleListFragment.setArguments(mBundle);
        }
        return mScheduleListFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.layout_list;
    }

    @Override
    public void initIds() {
        mListView = (StickyListHeadersListView) view.findViewById(R.id.attendeeListView);
        noDataAvailableTxt = (TextView) view.findViewById(R.id.txtNoData);
        mListView.setEmptyView(noDataAvailableTxt);
    }

    @Override
    public void setListeners() {
        ListWatcher.getInstance().registerListener(this);
        mListView.setOnItemClickListener(mListClick);
    }

    @Override
    public void setUp() {
        setupSearchBar(true, "Search Sessions");
        handleDateLayout(mLeftClick, mRightClick);
        getData();
        getPresenter().fetchDateList(trackid, getActivity());
        getPresenter().fetchList(trackid, null, null, getActivity());
        addFilter(filterTextWatcher);
    }

    @Override
    public ScheduleListPresenter setPresenter() {
        return new ScheduleListPresenter(this);
    }

    @Override
    public String getScreenName() {
        return "SCHEDULE_VIEW";
    }

    @Override
    public String getBannerName() {
        return null;
    }

    public void getData() {
        trackid = getArguments().getString("trackId");
        trackid = trackid.equals("0") ? null : trackid;
    }

    @Override
    public void showList(List<ScheduleData> mList) {

        Log.i("ScheduleList", "showList: List refreshed \nStatus: " + mList.get(0).isSessionFav());
        ScheduleAdapter mAdapter = new ScheduleAdapter(getActivity());
        mAdapter.addAll(mList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void showDate(List<String> mDateList) {
        mdateList = new ArrayList<>();
        mdateList.addAll(mDateList);
        if (!mdateList.isEmpty()) {
            setDate(mDateList.get(0));
        }
    }

    public void setDate(String date) {

        String[] months = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};


        String[] dateArr = date.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(intValue(dateArr[0]), intValue(dateArr[1]) - 1, intValue(dateArr[2]));

        int dd = cal.get(Calendar.DATE);
        String mm = months[cal.get(Calendar.MONTH)];
        int yy = cal.get(Calendar.YEAR);
        String eventDate = mm + " " + dd + ", " + yy;

        String dayOfWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());

        dateTxt.setText(dayOfWeek + " " + eventDate);

    }

    public Integer intValue(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public void refreshList() {
        String searchQuery = searchEditTxt.getText().toString();

        searchQuery = searchQuery.equals("") ? null : searchQuery;
        getPresenter().fetchList(trackid, searchQuery, mdateList.get(counter), getActivity());
    }
}
