package com.grupio.attendee;

/**
 * Created by JSN on 25/7/16.
 */
public interface ListPresenterInter {
    void fetchList(String queryStr, String cateogory);

    void fetchListFromServer();

    void fetchCategoryList();
}
