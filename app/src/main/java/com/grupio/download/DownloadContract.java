package com.grupio.download;

import android.content.Context;

import com.grupio.data.DownloadedResource;

import java.util.List;

/**
 * Created by JSN on 28/11/16.
 */

public interface DownloadContract {

    interface View {
        void showProgress(String msg);

        void hideProgress();

        void showCustomProgress();

        void hideCustomProgress();

        void showList(List<DownloadedResource> mData);

        void onFailure(String msg);

        void showDownlaodProgress(int[] progress, String[] name);

        void allDownloadComplete();
    }

    interface Presenter {

        void fetchResourceList(Context mContext);

        void fetchAllResourceFromServer(Context mContext);

        void downloadAllResources(Context mContext, List<DownloadedResource> mData);
    }

    interface Interactor {

        void fetchResourceList(Context mContext, OnInteration mListener);

        void fetchAllResourceFromServer(Context mContext, OnInteration mListener);

        void downloadAllResources(Context mContext, List<DownloadedResource> mData, OnInteration mListener);
    }

    interface OnInteration {
        void onListFetch(List<DownloadedResource> mData);

        void onFailure(String msg);

        void onDownload(int[] progress, String[] name);

        void onDownloadComplete();
    }

}
