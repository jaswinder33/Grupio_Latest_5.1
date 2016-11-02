package com.grupio.attendee.message;

import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by JSN on 18/10/16.
 */

public interface MessageView {
    void showProgress();

    void hideProgress();

    void setAttendeeName(String name, List<AttendeesData> mList);

    void setSendTo(String name);

    void onFailure(String msg);

    void onSuccess();
}
