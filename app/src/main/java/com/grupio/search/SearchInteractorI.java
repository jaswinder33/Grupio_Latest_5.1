package com.grupio.search;

import android.content.Context;

import com.grupio.base.BaseInteractor;
import com.grupio.dao.AttendeeDAO;
import com.grupio.dao.EventDAO;
import com.grupio.dao.ExhibitorDAO;
import com.grupio.dao.SessionDAO;
import com.grupio.dao.SpeakerDAO;
import com.grupio.dao.SponsorDAO;
import com.grupio.db.EventTable;
import com.grupio.interfaces.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 7/12/16.
 */

public class SearchInteractorI extends BaseInteractor implements SearchContract.InteractorI {
    @Override
    public void fetchData(Context mContext, String queryStr, SearchContract.OnInteraction mListener) {

        List<Person> mPersonList = new ArrayList<>();

        if (queryStr != null) {
            boolean isFirstName = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER).equals("firstname_lastname");

            //Add attendees
            mPersonList.addAll(AttendeeDAO.getInstance(mContext).searchAttendeeByName(null, queryStr, isFirstName));

            //Add Sessions
            mPersonList.addAll(SessionDAO.getInstance(mContext).searchSessions(queryStr, isFirstName));

            //Add Speakers
            mPersonList.addAll(SpeakerDAO.getInstance(mContext).searchSpeakerByName(null, queryStr, isFirstName));

            //Add Sponsors
            mPersonList.addAll(SponsorDAO.getInstance(mContext).getSponsorList(queryStr));

            //Add Exhibitors
            mPersonList.addAll(ExhibitorDAO.getInstance(mContext).searchExhibitorByName(null, queryStr, false));
        }

        mListener.onListFetch(mPersonList);

    }
}
