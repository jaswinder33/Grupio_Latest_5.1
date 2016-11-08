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

        trackName = !trackName.equals("") ? trackName : null;

        mListener.onListFetch(SessionDAO.getInstance(mContext).fetchSessionList(date, trackName, searchQuery));
    }

    @Override
    public void fetchDateList(String trackId, Context mContext, ScheduleListContract.onInteraction mListener) {
        String trackName = SessionTracksDAO.getInstance(mContext).getTrackName(trackId);
        trackName = !trackName.equals("") ? trackName : null;
        mListener.onDateListFetch(SessionDAO.getInstance(mContext).getDateList(trackName));
    }


}
