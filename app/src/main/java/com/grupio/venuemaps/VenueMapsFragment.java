package com.grupio.venuemaps;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.data.MapsData;
import com.grupio.data.SpeakerData;
import com.grupio.fragments.BaseFragment;
import com.grupio.logistics.DocumentController;

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

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MapsData mapsData = (MapsData) adapterView.getAdapter().getItem(i);

            DocumentController<MapsData, MapsData> mController = new DocumentController<>(new MapsData(), new MapsData(), getActivity());
            mController.handleDocument(mapsData);
        }
    };
    private ListView mListview;
    private TextView noDataAvailable;
    private T type;
    private VenueMapsFragment mVenueMapsFragment;

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
        return new VenueMapsPresenter(type, this, getActivity());
    }

    @Override
    public void initIds() {
        mListview = (ListView) view.findViewById(R.id.logisticsList);
        noDataAvailable = (TextView) view.findViewById(R.id.noDataAvailable);
        mListview.setEmptyView(noDataAvailable);
    }

    @Override
    public void setListeners() {
        mListview.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void showList(List<?> mList) {
        if (type instanceof MapsData) {
            MapsListAdapter mAdapter = new MapsListAdapter(getActivity());
            mAdapter.addAll((List<MapsData>) mList);
            mListview.setAdapter(mAdapter);
        }
    }
}
