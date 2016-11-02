package com.grupio.attendee;

import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by JSN on 25/7/16.
 */
public interface ControllerInter {

    void fetchListFromServer(onTaskComplete mListener);

    void fetchListFromDB(String queryStr, String cateogory, onTaskComplete mListener);

    interface onTaskComplete {
        void onListFetch(List<? extends Person> mlist);

        void onCategoryFetch(List<String> mList);

        void onFailure(String msg);
    }


}
