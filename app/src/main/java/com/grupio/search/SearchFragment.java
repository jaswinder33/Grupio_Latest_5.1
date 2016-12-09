package com.grupio.search;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.ListConstant;
import com.grupio.attendee.ListDetailActivity;
import com.grupio.attendee.ListWatcher;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.ScheduleData;
import com.grupio.data.SpeakerData;
import com.grupio.data.SponsorData;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.Person;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by mani on 7/12/16.
 */

public class SearchFragment extends BaseFragment<SearchPresenter> implements SearchContract.IView, ListWatcher.Watcher {
    AdapterView.OnItemClickListener mListner = (adapterView, view1, i, l) -> {

        Person mPerson = (Person) adapterView.getAdapter().getItem(i);

        Intent mIntent = new Intent(getActivity(), ListDetailActivity.class);

        if (mPerson instanceof AttendeesData) {
            mIntent.putExtra("id", ((AttendeesData) mPerson).getAttendee_id());
            mIntent.setType("attendee");
        } else if (mPerson instanceof SpeakerData) {
            mIntent.putExtra("id", ((SpeakerData) mPerson).getAttendee_id());
            mIntent.setType("speaker");
        } else if (mPerson instanceof ExhibitorData) {
            mIntent.putExtra("id", ((ExhibitorData) mPerson).getExhibitorId());
            mIntent.setType("exhibitor");
        } else if (mPerson instanceof SponsorData) {
            mIntent.putExtra("id", ((SponsorData) mPerson).sponsorId);
            mIntent.setType(ListConstant.SPONSOR);
        } else if (mPerson instanceof ScheduleData) {
            mIntent.setType(ListConstant.SESSION);
            mIntent.putExtra("id", ((ScheduleData) mPerson).getSession_id());
        }

        mIntent.putExtra("data", mPerson);
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(getActivity());

    };
    TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().length() > 0) {
                getPresenter().fetchData(getActivity(), charSequence.toString());
            } else {
                getPresenter().fetchData(getActivity(), null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    private StickyListHeadersListView mListView;
    private TextView noDataAvailable;

    @Override
    public int getLayout() {
        return R.layout.layout_list;
    }

    @Override
    public void initIds() {
        mListView = (StickyListHeadersListView) view.findViewById(R.id.attendeeListView);
        noDataAvailable = (TextView) view.findViewById(R.id.txtNoData);
        mListView.setEmptyView(noDataAvailable);
    }

    @Override
    public void setListeners() {
        mListView.setOnItemClickListener(mListner);
        ListWatcher.getInstance().registerListener(this);
    }

    @Override
    public void setUp() {
        setupSearchBar(true, "Search Attendee,Sessions, Speaker, Sponsors, Exhibitors");
        addFilter(mWatcher);
    }

    @Override
    public SearchPresenter setPresenter() {
        return new SearchPresenter(this);
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
    public void showList(List<Person> mPerson) {
        SearchAdapter mAdapter = new SearchAdapter(getActivity());
        mAdapter.setFullHeader(true);
        mAdapter.addAll(mPerson);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void refreshList() {
        String searchquery = searchEditTxt.getText().toString();
        getPresenter().fetchData(getActivity(), searchquery);
    }
}
