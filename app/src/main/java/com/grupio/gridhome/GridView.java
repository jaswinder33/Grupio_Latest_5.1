package com.grupio.gridhome;

import com.grupio.data.MenuData;

import java.util.List;

/**
 * Created by JSN on 21/9/16.
 */
public interface GridView {
    void showGrid(List<MenuData> menuList);

    void notifyAdapter();

    void showHeader(boolean flag);

    void showCalendarCount(String count);

    void alertCount(String count);

    void chatCount(String count);

    void messageCount(String count);

    void startUpdater();
}
