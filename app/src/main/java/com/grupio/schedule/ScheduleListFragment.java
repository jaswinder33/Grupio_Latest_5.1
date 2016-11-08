package com.grupio.schedule;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.data.ScheduleData;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.ClickHandler;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by JSN on 7/11/16.
 */

public class ScheduleListFragment extends BaseFragment<ScheduleListPresenter> implements ScheduleListContract.View {

    private static ScheduleListFragment mScheduleListFragment;
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

            List<ScheduleData> filterList = new ArrayList<>();

            if (s.toString().length() > 0) {


            } else {

            }
        }
    };
    AdapterView.OnItemClickListener mListClick = (adapterView, view1, i, l) -> {

    };
    private List<String> mdateList;
    private int counter = 0;
    private String trackid = null;
    ClickHandler mLeftClick = () -> {
        if (counter > 0) {
            getPresenter().fetchList(trackid, searchEditTxt.getText().toString(), mdateList.get(counter - 1), getActivity());
            dateTxt.setText(mdateList.get(counter - 1));
            counter--;
        }
    };
    ClickHandler mRightClick = () -> {
        if (counter < mdateList.size() - 1) {
            getPresenter().fetchList(trackid, searchEditTxt.getText().toString(), mdateList.get(counter + 1), getActivity());
            dateTxt.setText(mdateList.get(counter + 1));
            counter++;
        }
    };
    private TextView noDataAvailableTxt;
    private StickyListHeadersListView mListView;

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
        mListView.setOnItemClickListener(mListClick);
    }

    @Override
    public void setUp() {
        setupSearchBar(true, "Search Sessions");
        handleDateLayout(mLeftClick, mRightClick);
        getData();
        getPresenter().fetchDateList(getActivity());
        getPresenter().fetchList(trackid, searchEditTxt.getText().toString(), null, getActivity());
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
        ScheduleAdapter mAdapter = new ScheduleAdapter(getActivity());
        mAdapter.addAll(mList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void showDate(List<String> mDateList) {
        mdateList = new ArrayList<>();
        mdateList.addAll(mDateList);
        dateTxt.setText(mDateList.get(0));
    }
}
