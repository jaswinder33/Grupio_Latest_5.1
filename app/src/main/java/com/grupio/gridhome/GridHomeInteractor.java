package com.grupio.gridhome;

import android.content.Context;

import com.grupio.dao.EventDAO;
import com.grupio.dao.MenuDAO;
import com.grupio.data.MenuData;
import com.grupio.db.EventTable;
import com.grupio.session.ConstantData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 21/9/16.
 */
public class GridHomeInteractor implements GridInteractorImp{


    @Override
    public void fetchMenuList(OnInteractComplete listener, Context context) {

        ConstantData.EVENT_ID = EventDAO.getInstance(context).getValue(EventTable.EVENT_ID);

        List<MenuData> mList = new ArrayList<>();
        mList.addAll(MenuDAO.getInstance(context).fetchMenu());

        if(mList != null && !mList.isEmpty()){
            listener.onMenuList(mList);
        }

    }

    @Override
    public void fetchMessageCount(OnInteractComplete listener, Context context) {

    }

    @Override
    public void fetchAlertCount(OnInteractComplete listener, Context context) {

    }

    @Override
    public void fetchCalendarCount(OnInteractComplete listener, Context context) {

    }

    @Override
    public void updateMeeting(OnInteractComplete listener, Context context) {

    }

    @Override
    public void updateEvent(OnInteractComplete listener, Context context) {

    }
}
