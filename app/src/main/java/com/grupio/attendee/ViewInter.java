package com.grupio.attendee;

import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by JSN on 25/7/16.
 */
public interface ViewInter {
    void showProgress();

    void hideProgress();

    void showList(List<? extends Person> mList);

    void showCategory(List<String> mList);

    void hideCategory();

    void reload();

    void notifyAdapter();

    void onFailure(String msg);
}
