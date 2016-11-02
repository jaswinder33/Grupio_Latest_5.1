package com.grupio.attendee.message;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.data.AttendeesData;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ChooseAttendeeActivity extends BaseActivity<ChooseAttendeePresenter> implements ChooseAttendeeView {

    TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                getPresenter().fetchAttendeeList(ChooseAttendeeActivity.this, s.toString(), null);
            } else {
                getPresenter().fetchAttendeeList(ChooseAttendeeActivity.this, null, null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private StickyListHeadersListView mAttendeeList;
    private TextView noDataAvailable;
    private String attendeeId = "";
    private Button b1, b2, b3;
    private ChooseAttendeeListAdapter mAdapter;

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public void handleRightBtnClick() {
        searchEditTxt.setText("");
        getPresenter().refreshAttendeeList(this);
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
        mAttendeeList = (StickyListHeadersListView) findViewById(R.id.attendeeList);
        noDataAvailable = (TextView) findViewById(R.id.noDataAvailable);
        mAttendeeList.setEmptyView(noDataAvailable);
    }

    @Override
    public void setListeners() {
        handleRightBtn(true, REFRESH);
        setupSearchBar(true, "Search Attendee");
        addFilter(mWatcher);
    }

    @Override
    public ChooseAttendeePresenter setPresenter() {
        return new ChooseAttendeePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_choose_attendee;
    }

    @Override
    public void setUp() {
        getPresenter().fetchAttendeeList(this, null, null);
    }

    @Override
    public void showProgress() {
        showProgressDialog("Loading...");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showAttendeeList(List<AttendeesData> mList) {
        setAdapter(mList, getData());
    }

    @Override
    public void deliverAttendeeList(List<AttendeesData> mList) {
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAdapter(List<AttendeesData> mList, ArrayList<AttendeesData> selectedAttendee) {
        mAttendeeList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD || Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD_MR1) {
            mAttendeeList.setOnScrollListener(new AbsListView.OnScrollListener() {
                private boolean flinging = false;
                private boolean reachedEnd = false;

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    flinging = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING);
                    reachedEnd = false;
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (reachedEnd && flinging && (firstVisibleItem + visibleItemCount < totalItemCount)) {
                        mAttendeeList.setSelection(mAttendeeList.getAdapter().getCount() - 1);
                    } else reachedEnd = firstVisibleItem + visibleItemCount == totalItemCount;
                }
            });
        }

        for (int i = 0; i < selectedAttendee.size(); i++) {
            for (int j = 0; j < mList.size(); j++) {
                if (mList.get(j).getAttendee_id().equals(selectedAttendee.get(i).getAttendee_id())) {
                    mList.get(j).setAttendeeSelected(true);
                    break;
                }
            }
        }

        mAdapter = new ChooseAttendeeListAdapter(ChooseAttendeeActivity.this, selectedAttendee, attendeeId);
        mAdapter.addAll(mList);
        mAttendeeList.setAdapter(mAdapter);
    }

    public ArrayList<AttendeesData> getData() {
        ArrayList<AttendeesData> mAttendeeList = new ArrayList<>();
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mAttendeeList = (ArrayList<AttendeesData>) mBundle.get("attendeeList");
            attendeeId = mBundle.getString("attendee_id");
        }
        return mAttendeeList;

    }

    @Override
    public void onBackPressed() {
        if (mAdapter != null) {
            Intent mIntent = new Intent();
            mIntent.putExtra("attendeeList", mAdapter.getSelectedAttendee());
            setResult(RESULT_OK, mIntent);
        }
        super.onBackPressed();
    }
}
