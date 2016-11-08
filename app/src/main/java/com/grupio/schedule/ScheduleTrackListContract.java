package com.grupio.schedule;

import android.content.Context;

import com.grupio.data.TrackData;

import java.util.List;

/**
 * Created by mani on 27/10/16.
 */

public interface ScheduleTrackListContract {

    interface ScheduleView {
        void showList(List<TrackData> mList);
    }

    interface SchedulePresenterImp {
        void fetchList(Context mContext);
    }

    interface ScheduleInteractor {
        void fetchList(Context mContext, ScheduleOnInteraction mListener);
    }

    interface ScheduleOnInteraction {
        void onListFetch(List<TrackData> mList);
    }
}
