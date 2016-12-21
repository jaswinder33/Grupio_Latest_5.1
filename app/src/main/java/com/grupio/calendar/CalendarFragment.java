package com.grupio.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.ListConstant;
import com.grupio.attendee.ListDetailActivity;
import com.grupio.attendee.ListWatcher;
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

public class CalendarFragment extends BaseFragment<CalendarListPresenter> implements CalendarListContract.IView, ListWatcher.Watcher, View.OnClickListener {


    /**
     * Handle list element click. It will navigate to Session Detail Screen or Meeting Detail screen according to the element type of list. If element belongs to session
     * it will navigate to session detail screen otherwise it will consider it as meeting and navigate to meeting detail screen.
     */
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

            Bundle mBundle = new Bundle();
            mBundle.putString("id", mMeetingData.id);
            mBundle.putSerializable("data", mMeetingData);

            goToNextScreen(mBundle, MeetingDetailsActivity.class);

        }
    };

    private TextView noDataAvailableTxt;
    private StickyListHeadersListView mListView;
    private int counter = 0;
    /**
     * Handle click of left arrow
     */
    ClickHandler mLeftClick = () -> {
        if (counter > 0) {
            List<String> dateList = new ArrayList<>();
            dateList.addAll(getPresenter().getDateList(getActivity()));
            getPresenter().fetchList(getActivity(), dateList.get(counter - 1));
            showDate(dateList.get(counter - 1));
            counter--;
        }
    };
    /**
     * Handle click of right arrow
     */
    ClickHandler mRightClick = () -> {
        List<String> dateList = new ArrayList<>();
        dateList.addAll(getPresenter().getDateList(getActivity()));

        if (counter < dateList.size() - 1) {
            getPresenter().fetchList(getActivity(), dateList.get(counter + 1));
            showDate(dateList.get(counter + 1));
            counter++;
        }
    };
    private RelativeLayout viewInvitation;

    @Override
    public int getLayout() {
        return R.layout.layout_list;
    }


    @Override
    public void initIds() {
        mListView = (StickyListHeadersListView) view.findViewById(R.id.attendeeListView);
        noDataAvailableTxt = (TextView) view.findViewById(R.id.noDataAvailable);
        mListView.setEmptyView(noDataAvailableTxt);
        viewInvitation = (RelativeLayout) view.findViewById(R.id.invitationHeader);
        viewInvitation.setVisibility(View.VISIBLE);
    }

    @Override
    public void setListeners() {
        ListWatcher.getInstance().registerListener(this);
        mListView.setOnItemClickListener(mListClick);
        viewInvitation.setOnClickListener(this);
    }

    @Override
    public void setUp() {
        handleDateLayout(mLeftClick, mRightClick);
        getPresenter().fetchListFromServer(getActivity());
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
    public void showList(List<Person> mList) {
        Utility.printLog("Calendar", mList.size() + "");
        CalendarListAdapter mAdapter = new CalendarListAdapter(getActivity());
        mAdapter.addAll(mList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void showDate(String date) {
        dateTxt.setText(date);
    }

    /**
     * This refresh happens when user unlike session from session detail screen.
     * <p>
     * Flow:
     * MyCalendar menu -> Click on any Session -> Session Detail Screen.
     * <p>
     * On Session detail screen unlike session which will trigger callback which lands on this method refreshList() and it will
     * remove that session from the list.
     */
    @Override
    public void refreshList() {
        getPresenter().refreshList(counter, getActivity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invitationHeader:

                if (!loginRequired(getString(R.string.invitation))) {
                    goToNextScreen(new Bundle(), ViewInvitationActivity.class);
                }

                break;
        }
    }

//    /**
//     * Refresh List from Meeting Detail Screen.
//     *
//     * operations:
//     *  -1 : Deleted
//     *  0 : Nothing
//     *  1 : Accepted
//     *
//     * @param data
//     */
//    @Override
//    public void refreshList(Integer data) {
//        getPresenter().refreshList(data, counter, getActivity());
//    }
}
