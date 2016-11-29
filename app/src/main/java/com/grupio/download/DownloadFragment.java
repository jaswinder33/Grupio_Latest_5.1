package com.grupio.download;

import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.data.LogisticsData;
import com.grupio.fragments.BaseFragment;

import java.util.List;

/**
 * Created by JSN on 28/11/16.
 */

public class DownloadFragment extends BaseFragment<DownloadPresenter> implements DownloadContract.View {
    private ListView mListview;
    private TextView noDataAvailable;

    @Override
    public int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void initIds() {
        mListview = (ListView) view.findViewById(R.id.logisticsList);
        noDataAvailable = (TextView) view.findViewById(R.id.noDataAvailable);
        mListview.setEmptyView(noDataAvailable);
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void setUp() {
        getPresenter().fetchResourceList(getActivity());
    }

    @Override
    public DownloadPresenter setPresenter() {
        return new DownloadPresenter(this);
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
    public void showList(List<LogisticsData> mData) {

    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void showDownlaodProgress(int[] progress, String[] name) {

    }
}
