package com.grupio.calendar;

import android.content.Context;

import com.grupio.Utils.Utility;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.MeetingAPI;
import com.grupio.base.BaseInteractor;
import com.grupio.dao.MeetingDAO;
import com.grupio.dao.SessionDAO;
import com.grupio.interfaces.Person;
import com.grupio.session.Preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 12/12/16.
 */

public class CalendarListInteractor extends BaseInteractor implements CalendarListContract.IInteractor {

    @Override
    public void fetchListFromServer(Context context, CalendarListContract.IOnInteraction listener) {
        if (Utility.hasInternet(context) && Preferences.getInstances(context).getAttendeeId() != null) {
            new MeetingAPI(context, new APICallBackWithResponse() {
                @Override
                public void onSuccess(String response) {
                    fetchList(context, null, listener);
                    fetchDateList(context, listener);
                }

                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure(String msg) {
                    listener.onFailure(msg);
                }
            }).doCall();
        } else {
            fetchList(context, null, listener);
            fetchDateList(context, listener);
        }
    }

    @Override
    public void fetchList(Context context, String date, CalendarListContract.IOnInteraction listener) {
        listener.onListFetch(getList(context, date));
    }

    @Override
    public List<Person> getList(Context context, String date) {

        List<Person> mList = new ArrayList<>();

        if (Preferences.getInstances(context).getAttendeeId() != null) {
            mList.addAll(MeetingDAO.getInstance(context).getMeetingList(date));
        }

        mList.addAll(SessionDAO.getInstance(context).getLikedSession(date));

        return mList;
    }

    @Override
    public List<String> getDataList(Context context) {
        return MeetingDAO.getInstance(context).getDateList();
    }

    @Override
    public void refreshList(int counter, Context context, CalendarListContract.IOnInteraction listener) {

        List<String> dateList = new ArrayList<>();
        dateList.addAll(getDataList(context));

        List<Person> mScheduleList = getList(context, dateList.get(counter));

        if (dateList.size() > 0) {
            if (mScheduleList.isEmpty()) {
                fetchList(context, dateList.get(counter == 0 ? ++counter : --counter), listener);
            } else {
                fetchList(context, dateList.get(counter), listener);
            }
        } else {
            fetchList(context, null, listener);
        }


//        if (operation == ListWatcher.getInstance().getExtendedListener().ACCEPTED) {
//            fetchList(context, dateList.get(counter), listener);
//        } else if (operation == ListWatcher.getInstance().getExtendedListener().DELETE) {
//
//            if(dateList.size() > 0 ){
//                if(mScheduleList.isEmpty()){
//                    fetchList(context, dateList.get(counter - 1), listener);
//                }else{
//                    fetchList(context, dateList.get(counter), listener);
//                }
//            }else{
//                fetchList(context, null, listener);
//            }
//        }
    }

    private void fetchDateList(Context context, CalendarListContract.IOnInteraction listener) {
        List<String> dateList = new ArrayList<>();
        dateList.addAll(getDataList(context));

        if (dateList != null && !dateList.isEmpty()) {
            listener.showDate(dateList.get(0));
        }
    }

}
