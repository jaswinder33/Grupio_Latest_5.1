package com.grupio.attendee.message;

import com.grupio.base.IBaseView;
import com.grupio.data.AttendeesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 19/10/16.
 */

public interface ChooseAttendeeView extends IBaseView {

    void showProgress();

    void hideProgress();

    void showAttendeeList(List<AttendeesData> mList);

    void deliverAttendeeList(List<AttendeesData> mList);

    void onFailure(String msg);

    void setAdapter(List<AttendeesData> mList, ArrayList<AttendeesData> selectedAttendee);
}
