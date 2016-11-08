package com.grupio.schedule;

import android.content.Context;

import com.grupio.dao.SessionTracksDAO;
import com.grupio.data.TrackData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mani on 7/11/16.
 */

public class ScheduleTrackListInteractor implements ScheduleTrackListContract.ScheduleInteractor {


    @Override
    public void fetchList(Context mContext, ScheduleTrackListContract.ScheduleOnInteraction mListener) {
        List<TrackData> trackList = new ArrayList<>();
        trackList.addAll(SessionTracksDAO.getInstance(mContext).getTrackList());


        trackList.get(0).showAllSession = true;


        TrackData td = new TrackData();
        td.id = "0";
//        td.track = LocalisationDataProcessor.SHOW_ALL_SESSIONS;
        td.track = "Show All Sessions";
        td.color = "#ffffff";
        td.category = "";
        td.order = "0";
        td.showAllSession = false;
        trackList.add(0, td);
        mListener.onListFetch(trackList);
    }
}
