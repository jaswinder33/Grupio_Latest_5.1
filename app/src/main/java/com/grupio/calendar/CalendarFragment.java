package com.grupio.calendar;

import android.content.Intent;
import android.widget.AdapterView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.ListConstant;
import com.grupio.attendee.ListDetailActivity;
import com.grupio.data.MeetingData;
import com.grupio.data.ScheduleData;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.ClickHandler;
import com.grupio.interfaces.Person;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by JSN on 12/12/16.
 */

public class CalendarFragment extends BaseFragment<CalendarListPresenter> implements CalendarListContract.IView {

    AdapterView.OnItemClickListener mListClick = (adapterView, view1, i, l) -> {


        Person obj = (Person) adapterView.getAdapter().getItem(i);

        Intent mIntent = new Intent();
        if (obj instanceof ScheduleData) {
            ScheduleData mScheduleData = (ScheduleData) obj;
            mIntent.setClass(getActivity(), ListDetailActivity.class);
            mIntent.setType(ListConstant.SESSION);
            mIntent.putExtra("id", mScheduleData.getSession_id());
            mIntent.putExtra("data", obj);
            startActivity(mIntent);
            SlideOut.getInstance().startAnimation(getActivity());
        } else if (obj instanceof MeetingData) {
            MeetingData mMeetingData = (MeetingData) obj;
        }

    };
    private TextView noDataAvailableTxt;
    private StickyListHeadersListView mListView;
    private int counter = 0;
    ClickHandler mLeftClick = () -> {
        if (counter > 0) {
            List<String> dateList = new ArrayList<>();
            dateList.addAll(getPresenter().getDateList(getActivity()));
            getPresenter().fetchList(getActivity(), dateList.get(counter - 1));
            setDate(dateList.get(counter - 1));
            counter--;
        }
    };
    ClickHandler mRightClick = () -> {
        List<String> dateList = new ArrayList<>();
        dateList.addAll(getPresenter().getDateList(getActivity()));

        if (counter < dateList.size() - 1) {
            getPresenter().fetchList(getActivity(), dateList.get(counter + 1));
            setDate(dateList.get(counter + 1));
            counter++;
        }
    };

    @Override
    public int getLayout() {
        return R.layout.layout_list;
    }

    @Override
    public void initIds() {
        mListView = (StickyListHeadersListView) view.findViewById(R.id.attendeeListView);
        noDataAvailableTxt = (TextView) view.findViewById(R.id.noDataAvailable);
        mListView.setEmptyView(noDataAvailableTxt);
    }

    @Override
    public void setListeners() {
//        ListWatcher.getInstance().registerListener(this);
        mListView.setOnItemClickListener(mListClick);
    }

    @Override
    public void setUp() {
        handleDateLayout(mLeftClick, mRightClick);
        getPresenter().fetchListFromServer(getActivity());
        setDate(getPresenter().getDateList(getActivity()).get(0));
    }

    @Override
    public CalendarListPresenter setPresenter() {
        return new CalendarListPresenter(this);
    }

    @Override
    public String getScreenName() {
        return "MEETING_LIST_VIEW";
    }

    @Override
    public String getBannerName() {
        return "mycalendar";
    }

    @Override
    public void showProgress(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void showList(List<Person> mList) {
        Utility.printLog("Calendar", mList.size() + "");
        CalendarListAdapter mAdapter = new CalendarListAdapter(getActivity());
        mAdapter.addAll(mList);
        mListView.setAdapter(mAdapter);
    }

    private void setDate(String date) {
        dateTxt.setText(date);
    }

}
