package com.grupio.gridhome;

import com.grupio.data.MenuData;

import java.util.List;

/**
 * Created by JSN on 21/9/16.
 */
public interface GridView {
    void showGrid(List<MenuData> menuList);

    void notifyAdapter();

    void notifyItem(int position);

    void showHeader(boolean flag);

    void showCalendarCount(String count, int position);

    void alertCount(String count, int position);

    void chatCount(String count, int position);

    void messageCount(String count, int position);

    void startUpdater();
}
