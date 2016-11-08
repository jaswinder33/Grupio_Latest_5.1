package com.grupio.schedule;

import android.content.Context;

import com.grupio.data.ScheduleData;

import java.util.List;

/**
 * Created by JSN on 7/11/16.
 */

public class ScheduleListContract {

    interface View {
        void showList(List<ScheduleData> mList);

        void showDate(List<String> mDateList);
    }

    interface Presenter {
        void fetchList(String trackId, String searchQuery, String date, Context mContext);

        void fetchDateList(String trackId, Context mContext);
    }

    interface Interactor {
        void fetchlist(String trackId, String searchQuery, String date, Context mContext, onInteraction mListener);

        void fetchDateList(String trackId, Context mContext, onInteraction mListener);
    }

    interface onInteraction {
        void onListFetch(List<ScheduleData> mList);

        void onDateListFetch(List<String> mDateList);
    }

}
