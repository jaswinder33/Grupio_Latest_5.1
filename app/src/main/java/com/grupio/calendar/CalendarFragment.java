package com.grupio.calendar;

import android.widget.TextView;

import com.grupio.R;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.Person;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by JSN on 12/12/16.
 */

public class CalendarFragment extends BaseFragment<CalendarListPresenter> implements CalendarListContract.IView {

    private TextView noDataAvailableTxt;
    private StickyListHeadersListView mListView;

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
//        mListView.setOnItemClickListener(mListClick);
    }

    @Override
    public void setUp() {
        getPresenter().fetchList(getActivity(), null);
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

    }
}
