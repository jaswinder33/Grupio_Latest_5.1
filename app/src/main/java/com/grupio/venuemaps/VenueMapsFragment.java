package com.grupio.venuemaps;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.WebViewActivity;
import com.grupio.animation.SlideOut;
import com.grupio.data.AlertData;
import com.grupio.data.LiveData;
import com.grupio.data.MapsData;
import com.grupio.data.SpeakerData;
import com.grupio.data.SurveyData;
import com.grupio.fragments.BaseFragment;
import com.grupio.logistics.DocumentController;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment handles 4 menus
 * VENUEMAPS
 * LIVE
 * SURVEY
 * ALERT
 * <p>
 * All are of similar type. These menus first have simple list of elements and then opens with webveiw.
 */
public class VenueMapsFragment<T> extends BaseFragment<VenueMapsPresenter> implements VenueMapContract.MapsView {


    public static final String TAG = "VenueMapsFragment";
    AdapterView.OnItemClickListener alertListClickListener = (AdapterView<?> adapterView, View view, int position, long l) -> {
        AlertData mData = (AlertData) adapterView.getAdapter().getItem(position);
        ((VenueMapActivity) getActivity()).new CustomDialog(() -> {
            getPresenter().markAlertRead(mData.getAlertId(), getActivity());
        }).singledBtnDialog(true).show(mData.getNotificationText());

    };
    AdapterView.OnItemClickListener surveyListClickListener = (AdapterView<?> adapterView, View view, int position, long l) -> {
    };
    AdapterView.OnItemClickListener liveFeedClickListener = (AdapterView<?> adapterView, View view, int position, long l) -> {

        LiveData liveData = (LiveData) adapterView.getAdapter().getItem(position);

        Intent mIntent = new Intent(getActivity(), WebViewActivity.class);
        mIntent.putExtra("url", liveData.getUrl());
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(getActivity());


    };
    AdapterView.OnItemClickListener mapListClickListener = (AdapterView<?> adapterView, View view, int position, long l) -> {
        MapsData mapsData = (MapsData) adapterView.getAdapter().getItem(position);
        DocumentController<MapsData, MapsData> mController = new DocumentController<>(new MapsData(), new MapsData(), getActivity());
        mController.handleDocument(mapsData);
    };
    private ListView mListview;
    private TextView noDataAvailable;
    private T type;
    private VenueMapsFragment mVenueMapsFragment;
    private List<AlertData> mAlertList = new ArrayList<>();
    private AlertAdapter mAlertAdapter;

    VenueMapsFragment() {
    }

    VenueMapsFragment(T type, Bundle mBundle) {
        this.type = type;
        mVenueMapsFragment = new VenueMapsFragment();
        if (mBundle != null) {
            mVenueMapsFragment.setArguments(mBundle);
        }
    }

    @Override
    public String getScreenName() {
        if (type instanceof MapsData) {
            return "MAP_LIST_VIEW";
        } else if (type instanceof SpeakerData) {
            return "LIVE_VIEW";
        }

        return "";

    }

    @Override
    public String getBannerName() {
        if (type instanceof MapsData) {
            return "maps";
        } else if (type instanceof SpeakerData) {
            return "live";
        }

        return "";
    }

    @Override
    public VenueMapsPresenter setPresenter() {
        return new VenueMapsPresenter(this);
    }

    @Override
    public void initIds() {
        mListview = (ListView) view.findViewById(R.id.logisticsList);
        noDataAvailable = (TextView) view.findViewById(R.id.noDataAvailable);
        mListview.setEmptyView(noDataAvailable);
    }

    @Override
    public void setListeners() {
        if (type instanceof MapsData) {
            mListview.setOnItemClickListener(mapListClickListener);
        } else if (type instanceof LiveData) {
            mListview.setOnItemClickListener(liveFeedClickListener);
        } else if (type instanceof SurveyData) {
            mListview.setOnItemClickListener(surveyListClickListener);
        } else if (type instanceof AlertData) {
            mListview.setOnItemClickListener(alertListClickListener);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void setUp() {
        getPresenter().fetchList(type, getActivity());
    }

    @Override
    public void showList(List<?> mList) {
        if (type instanceof MapsData) {
            MapsListAdapter mMapListAdapter = new MapsListAdapter(getActivity());
            mMapListAdapter.addAll((List<MapsData>) mList);
            mListview.setAdapter(mMapListAdapter);
        } else if (type instanceof AlertData) {

            mAlertList = new ArrayList<>();
            mAlertList.addAll((List<AlertData>) mList);

            mAlertAdapter = new AlertAdapter(getActivity());
            mAlertAdapter.addAll(mAlertList);
            mListview.setAdapter(mAlertAdapter);

        } else if (type instanceof LiveData) {

            LiveFeedAdapter mAdapter = new LiveFeedAdapter(getActivity());
            mAdapter.addAll((List<LiveData>) mList);
            mListview.setAdapter(mAdapter);

            Log.i(TAG, "showList: Live List:" + mList.size());
        } else if (type instanceof SurveyData) {
            Log.i(TAG, "showList: Survey List:" + mList.size());
        }
    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void showProgress() {
        showProgressDialog("");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageMarked(String id) {
        for (int i = 0; i < mAlertAdapter.getCount(); i++) {
            if (mAlertAdapter.getItem(i).getAlertId().equals(id)) {
                mAlertAdapter.getItem(i).setRead(true);
                mAlertAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
