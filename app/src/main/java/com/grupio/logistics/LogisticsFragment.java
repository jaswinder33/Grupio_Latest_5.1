package com.grupio.logistics;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.data.LogisticsData;
import com.grupio.fragments.BaseFragment;

import java.util.List;

/**
 * Created by JSN on 24/10/16.
 */

public class LogisticsFragment extends BaseFragment<LogisticsPresenter> implements LogisticsContract.View {


    AdapterView.OnItemClickListener mListner = (adapterView, view1, i, l) -> {
        LogisticsData mData = (LogisticsData) adapterView.getAdapter().getItem(i);

        DocumentController<LogisticsData, LogisticsData> mController = new DocumentController<>(new LogisticsData(), new LogisticsData(), getActivity());
        mController.handleDocument(mData);
    };
    private ListView mListview;
    private TextView noDataAvailable;

    public LogisticsFragment() {
    }

    public static LogisticsFragment getInstance(Bundle mBundle) {
        LogisticsFragment logisticsFragment = new LogisticsFragment();
        if (mBundle != null) {
            logisticsFragment.setArguments(mBundle);
        }
        return logisticsFragment;

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
    public LogisticsPresenter setPresenter() {
        return new LogisticsPresenter(this);
    }

    @Override
    public void initIds() {
        mListview = (ListView) view.findViewById(R.id.logisticsList);
        noDataAvailable = (TextView) view.findViewById(R.id.noDataAvailable);
        mListview.setEmptyView(noDataAvailable);
    }

    @Override
    public void setListeners() {
        mListview.setOnItemClickListener(mListner);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void setUp() {
        getPresenter().fetchList(getActivity(), getArguments().getString("type"));
    }

    @Override
    public void showList(List<LogisticsData> mData) {
        LogisticsAdapter<LogisticsData> mAdapter = new LogisticsAdapter(getActivity(), new LogisticsData());
        mAdapter.addAll(mData);
        mListview.setAdapter(mAdapter);
    }

}
