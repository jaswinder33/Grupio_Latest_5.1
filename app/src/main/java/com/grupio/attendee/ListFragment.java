package com.grupio.attendee;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.ExhibitorAdapter;
import com.grupio.R;
import com.grupio.animation.SlideOut;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.SpeakerData;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.Person;
import com.grupio.speakers.SpeakerAdapter;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * Created by JSN on 25/7/16.
 */
public class ListFragment<T extends Person> extends BaseFragment<ListPresenter> implements ViewInter, ListWatcher.Watcher {

    private T type;
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent mIntent = new Intent(getActivity(), ListDetailActivity.class);

            Person personData = null;
            personData = (Person) parent.getAdapter().getItem(position);
            if (type instanceof AttendeesData) {
                mIntent.putExtra("id", ((AttendeesData) personData).getAttendee_id());
                mIntent.setType("attendee");
            } else if (type instanceof SpeakerData) {
                mIntent.putExtra("id", ((SpeakerData) personData).getAttendee_id());
                mIntent.setType("speaker");
            } else if (type instanceof ExhibitorData) {
                mIntent.putExtra("id", ((ExhibitorData) personData).getExhibitorId());
                mIntent.setType("exhibitor");
            }

            mIntent.putExtra("data", personData);
            startActivity(mIntent);
            SlideOut.getInstance().startAnimation(getActivity());
        }
    };
    private ListFragment mListFragment;
    private Spinner mSpinner;
    Spinner.OnItemSelectedListener mSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            View v = mSpinner.getSelectedView();
            ((TextView) v).setTextColor(Color.BLACK);


            String category = (String) parent.getAdapter().getItem(position);

            String queryStr = searchEditTxt.getText().toString();
            queryStr = queryStr.equals("") ? null : queryStr;

            getPresenter().fetchList(queryStr, category);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String category = mSpinner.isShown() ? mSpinner.getSelectedItem().toString() : null;
            if (s.length() > 0) {
                getPresenter().fetchList(s.toString(), category);
            } else {
                getPresenter().fetchList(null, category);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private TextView noDataAvailableTxt;
    private StickyListHeadersListView mListView;
    private AttendeeListAdapter mAttendeeadapter;
    private SpeakerAdapter mSpeakerAdapter;
    private ExhibitorAdapter mExhibitorAdapter;

    public ListFragment() {
    }

    public ListFragment(T type, Bundle mBundle) {
        this.type = type;
        mListFragment = new ListFragment();
        if (mBundle != null) {
            mListFragment.setArguments(mBundle);
        }
    }

    @Override
    public String getScreenName() {
        if (type instanceof AttendeesData) {
            return "ATTENDEE_LIST_VIEW";
        } else if (type instanceof SpeakerData) {
            return "SPEAKER_LIST_VIEW";
        } else if (type instanceof ExhibitorData) {
            return "EXHIBITOR_LIST_VIEW";
        }

        return "";
    }

    @Override
    public String getBannerName() {

        if (type instanceof AttendeesData) {
            return "attendees";
        } else if (type instanceof SpeakerData) {
            return "speakers";
        } else if (type instanceof ExhibitorData) {
            return "exhibitors";
        }

        return "";

    }

    @Override
    public ListPresenter setPresenter() {
        return new ListPresenter(type, getActivity(), this);
    }

    @Override
    public void initIds() {
        mSpinner = (Spinner) view.findViewById(R.id.spinnerCat);
        mListView = (StickyListHeadersListView) view.findViewById(R.id.attendeeListView);
        noDataAvailableTxt = (TextView) view.findViewById(R.id.txtNoData);
        mListView.setEmptyView(noDataAvailableTxt);
    }

    @Override
    public int getLayout() {
        return R.layout.layout_list;
    }

    @Override
    public void setUp() {
        setAdapter();

        String locale = "";
        if (type instanceof AttendeesData) {
            locale = "Attendee...";
        } else if (type instanceof SpeakerData) {
//            locale = LocalisationDataProcessor.SPEAKERS;
            locale = "Speakers...";
        } else if (type instanceof ExhibitorData) {
//            locale = LocalisationDataProcessor.EXHIBITORS;
            locale = "Exhibitor";
        }

        setupSearchBar(true, "Loading " + locale);
        addFilter(mWatcher);
    }

    /**
     * Register listeners
     */
    @Override
    public void setListeners() {
        mListView.setOnItemClickListener(onItemClickListener);
//        mListView.setOnScrollListener(this);
        mSpinner.setOnItemSelectedListener(mSpinnerListener);

        ListWatcher.getInstance().registerListener(this);
    }

    @Override
    public void showProgress() {

        String locale = "";
        if (type instanceof AttendeesData) {
            locale = "Attendee";
        } else if (type instanceof SpeakerData) {
            locale = "Speakers";
        } else if (type instanceof ExhibitorData) {
//            locale = LocalisationDataProcessor.EXHIBITORS;
            locale = "Exhibitors";
        }

        showProgressDialog(locale);

    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    /**
     * Set adapter for list
     *
     * @param mList
     */
    @Override
    public void showList(List<? extends Person> mList) {

        if (type instanceof AttendeesData) {
            mAttendeeadapter = new AttendeeListAdapter(getActivity());
            mListView.setAdapter(mAttendeeadapter);

            List<AttendeesData> attendeesDataList = (List<AttendeesData>) mList;
            mAttendeeadapter.addAll(attendeesDataList);
        } else if (type instanceof SpeakerData) {

            mSpeakerAdapter = new SpeakerAdapter(getActivity());
            mListView.setAdapter(mSpeakerAdapter);

            List<SpeakerData> mSpeakerList = (List<SpeakerData>) mList;
            mSpeakerAdapter.addAll(mSpeakerList);
        } else if (type instanceof ExhibitorData) {

         /*   //TODO
            MyAdapter myAdapter = new MyAdapter(getActivity());
            List<ExhibitorData> mExhibitorList = (List<ExhibitorData>) mList;
            myAdapter.addAll(mExhibitorList);
            mListView.setAdapter(myAdapter);

            //TODO*/


            mExhibitorAdapter = new ExhibitorAdapter(getActivity());
            mListView.setAdapter(mExhibitorAdapter);

            List<ExhibitorData> mExhibitorList = (List<ExhibitorData>) mList;
            mExhibitorAdapter.addAll(mExhibitorList);

        }
        notifyAdapter();

    }

    @Override
    public void showCategory(List<String> mList) {
        mSpinner.setVisibility(View.VISIBLE);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mList);
        mSpinner.setAdapter(mAdapter);

    }

    @Override
    public void hideCategory() {
        mSpinner.setVisibility(View.GONE);
    }

    @Override
    public void reload() {
        searchEditTxt.setText("");
        getPresenter().fetchListFromServer();
    }

    public void setAdapter() {
        mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD || Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD_MR1) {
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                        mListView.setSelection(mListView.getAdapter().getCount() - 1);
                    } else reachedEnd = firstVisibleItem + visibleItemCount == totalItemCount;
                }
            });
        }
    }

    @Override
    public void notifyAdapter() {

        if (type instanceof AttendeesData) {
            mAttendeeadapter.notifyDataSetChanged();
        } else if (type instanceof SpeakerData) {
            mSpeakerAdapter.notifyDataSetChanged();
        } else if (type instanceof ExhibitorData) {
            mExhibitorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(String msg) {
        if (msg != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refreshList() {

        String category = mSpinner.getVisibility() == View.VISIBLE ? mSpinner.getSelectedItem().toString() : null;

        String queryStr = searchEditTxt.getText().toString();
        queryStr = queryStr.equals("") ? null : queryStr;

        getPresenter().fetchList(queryStr, category);
    }
}
