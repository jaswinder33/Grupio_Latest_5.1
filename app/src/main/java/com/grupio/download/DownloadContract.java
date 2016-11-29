package com.grupio.download;

import android.content.Context;

import com.grupio.data.LogisticsData;

import java.util.List;

/**
 * Created by JSN on 28/11/16.
 */

public interface DownloadContract {

    interface View {
        void showProgress(String msg);

        void hideProgress();

        void showList(List<LogisticsData> mData);

        void onFailure(String msg);

        void showDownlaodProgress(int[] progress, String[] name);
    }

    interface Presenter {

        void fetchResourceList(Context mContext);

        void fetchAllResourceFromServer(Context mContext);

        void downloadAllResources(Context mContext);
    }

    interface Interactor {

        void fetchResourceList(Context mContext, OnInteration mListener);

        void fetchAllResourceFromServer(Context mContext, OnInteration mListener);

        void downloadAllResources(Context mContext, OnInteration mListener);
    }

    interface OnInteration {
        void onListFetch(List<LogisticsData> mData);

        void onFailure(String msg);

        void onDownload(int[] progress, String[] name);
    }

}
