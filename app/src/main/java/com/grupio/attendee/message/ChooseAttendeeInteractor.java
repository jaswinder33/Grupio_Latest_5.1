package com.grupio.attendee.message;

import android.app.Activity;
import android.content.Context;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.apis.AttendeeAPI;
import com.grupio.dao.AttendeeDAO;
import com.grupio.dao.EventDAO;
import com.grupio.data.AttendeesData;
import com.grupio.db.EventTable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by JSN on 19/10/16.
 */

public class ChooseAttendeeInteractor implements ChooseAttendeeInteractorImp {

    @Override
    public void fetchAttendeeList(Context mContext, String queryStr, String cateogory, onAttendeeInteraction mListener) {
        fetchAttendeeListFromDb(mContext, queryStr, cateogory, mListener);
    }

    @Override
    public void refreshAttendeeList(Context mContext, onAttendeeInteraction mListener) {

        if (Utility.hasInternet(mContext)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isSuccess = false;
                    ExecutorService es = Executors.newSingleThreadExecutor();
                    Future task = es.submit(new AttendeeAPI(mContext));

                    try {
                        task.get();
                        es.isShutdown();
                        isSuccess = true;
                    } catch (InterruptedException e) {
                        isSuccess = false;
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        isSuccess = false;
                        e.printStackTrace();
                    }

                    boolean finalIsSuccess = isSuccess;
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalIsSuccess) {
                                fetchAttendeeList(mContext, null, null, mListener);
                            } else {
                                mListener.onFailure(mContext.getString(R.string.erroor_occured));
                            }
                        }
                    });
                }
            }).start();
        } else {
            mListener.onFailure(mContext.getResources().getText(R.string.no_internet).toString());
        }

    }


    public void fetchAttendeeListFromDb(Context mContext, String queryStr, String cateogory, onAttendeeInteraction mListener) {

        boolean isFirstName = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER).equals("y");

        List<AttendeesData> mlist = new ArrayList<>();

        if (queryStr == null) {
            if (cateogory == null || cateogory.equals("All")) {
                mlist.addAll(AttendeeDAO.getInstance(mContext).getAttendeeList(isFirstName, 0));
            } else {
                mlist.addAll(AttendeeDAO.getInstance(mContext).sortAttendeeByCategory(isFirstName, cateogory, 0));
            }
        } else {
            if (cateogory == null || cateogory.equals("All")) {
                mlist.addAll(AttendeeDAO.getInstance(mContext).searchAttendeeByName(null, queryStr, isFirstName));
            } else {
                mlist.addAll(AttendeeDAO.getInstance(mContext).searchAttendeeByName(cateogory, queryStr, isFirstName));
            }
        }

        mlist = mlist != null ? mlist : new ArrayList<>();

        mListener.onAttendeeListFetch(mlist);

    }
}
