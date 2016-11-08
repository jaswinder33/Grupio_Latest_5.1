package com.grupio.schedule;

import android.content.Context;

import com.grupio.dao.SessionDAO;
import com.grupio.dao.SessionTracksDAO;

/**
 * Created by JSN on 7/11/16.
 */

public class ScheduleListInteractor implements ScheduleListContract.Interactor {


    @Override
    public void fetchlist(String trackId, String searchQuery, String date, Context mContext, ScheduleListContract.onInteraction mListener) {

        String trackName = SessionTracksDAO.getInstance(mContext).getTrackName(trackId);

        mListener.onListFetch(SessionDAO.getInstance(mContext).fetchSessionList(date, searchQuery));
    }

    @Override
    public void fetchDateList(Context mContext, ScheduleListContract.onInteraction mListener) {
        mListener.onDateListFetch(SessionDAO.getInstance(mContext).getDateList());
    }


}
