package com.grupio.attendee.message;

import android.content.Context;

/**
 * Created by JSN on 19/10/16.
 */

public interface ChooseAttendeePresenterImp {

    void fetchAttendeeList(Context mContext, String queryStr, String cateogory);

    void refreshAttendeeList(Context mContext);

}
