package com.grupio.logistics;

import android.content.Context;

import com.grupio.data.LogisticsData;

import java.util.List;

/**
 * Created by JSN on 21/10/16.
 */

public interface LogisticsContract {

    interface View {
        void showList(List<LogisticsData> mData);
    }

    interface Presenter {
        void fetchList(Context mContext, String type);
    }

    interface Interactor {
        void fetchList(Context mContext, String type, OnInteraction mListener);
    }

    interface OnInteraction {
        void onListFetch(List<LogisticsData> mData);
    }
}
