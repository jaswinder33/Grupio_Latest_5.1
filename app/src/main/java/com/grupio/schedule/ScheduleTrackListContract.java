package com.grupio.schedule;

import android.content.Context;

import com.grupio.data.ScheduleData;

import java.util.List;

/**
 * Created by mani on 27/10/16.
 */

public interface ScheduleTrackListContract {

    interface ScheduleView {
        void showLis(List<ScheduleData> mList);
    }

    interface SchedulePresenterImp {
        void fetchList(Context mContext);
    }

    interface ScheduleInteractor {
        void fetchList(Context mContext, ScheduleOnInteraction mListener);
    }

    interface ScheduleOnInteraction {
        void onListFetch(List<ScheduleData> mList);
    }
}
