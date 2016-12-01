package com.grupio.login;

import android.content.Context;

import com.grupio.dao.EventDAO;
import com.grupio.data.AttendeesData;
import com.grupio.db.EventTable;
import com.grupio.helper.AttendeeProcessor;
import com.grupio.session.Preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 30/11/16.
 */

public class MyAccountInteractor implements MyAccountContract.Interactor {

    @Override
    public void fetchUserInfo(Context mContext, MyAccountContract.OnInteraction mOnInteraction) {

        String userInfo = Preferences.getInstances(mContext).getUserInfo();

        List<AttendeesData> mList = new ArrayList<>();

        AttendeeProcessor ap = new AttendeeProcessor();
        mList.addAll(ap.getAttendeesListFromJSON(mContext, userInfo, false));


        boolean isFirstName = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER).equals("firstname");
        mOnInteraction.showDetails(mList.get(0), isFirstName);


    }

    @Override
    public void updateImage(String url, Context mContext, MyAccountContract.OnInteraction mOnInteraction) {

    }
}
