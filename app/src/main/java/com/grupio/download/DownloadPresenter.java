package com.grupio.download;

import android.content.Context;

import com.grupio.data.DownloadedResource;

import java.util.List;

/**
 * Created by JSN on 28/11/16.
 */

public class DownloadPresenter implements DownloadContract.Presenter, DownloadContract.OnInteration {

    private DownloadContract.View mListener;
    private DownloadInteractor mInteractor;

    public DownloadPresenter(DownloadContract.View mListener) {
        this.mListener = mListener;
        mInteractor = new DownloadInteractor();
    }

    @Override
    public void onListFetch(List<DownloadedResource> mData) {
        mListener.hideProgress();
        mListener.showList(mData);
    }

    @Override
    public void onFailure(String msg) {
        mListener.hideProgress();
        mListener.onFailure(msg);
    }

    @Override
    public void onDownload(int[] progress, String[] name) {
        mListener.showDownlaodProgress(progress, name);
    }

    @Override
    public void onDownloadComplete() {
        mListener.allDownloadComplete();
    }

    @Override
    public void fetchResourceList(Context mContext) {
        mListener.showProgress("Loading list...");
        mInteractor.fetchResourceList(mContext, this);
    }

    @Override
    public void fetchAllResourceFromServer(Context mContext) {
        mListener.showProgress("Loading List...");
        mInteractor.fetchAllResourceFromServer(mContext, this);
    }

    @Override
    public void downloadAllResources(Context mContext, List<DownloadedResource> mData) {
        mListener.showCustomProgress();
        mInteractor.downloadAllResources(mContext, mData, this);
    }
}
