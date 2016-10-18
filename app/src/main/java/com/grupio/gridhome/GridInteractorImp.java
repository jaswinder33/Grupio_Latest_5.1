package com.grupio.gridhome;

import android.content.Context;

import com.grupio.data.MenuData;

import java.util.List;

/**
 * Created by JSN on 22/9/16.
 */
public interface GridInteractorImp {

    interface OnInteractComplete {

        void onMenuList(List<MenuData> menuList);
        void onMessageCount(String msgCount);
        void onAlertCount(String alertCount);
        void onCalendarCount(String calCount);
        void onUpdateMeeting();
        void onUpdateEvent();

    }

    void fetchMenuList(OnInteractComplete listener, Context context);
    void fetchMessageCount(OnInteractComplete listener, Context context);
    void fetchAlertCount(OnInteractComplete listener, Context context);
    void fetchCalendarCount(OnInteractComplete listener, Context context);
    void updateMeeting(OnInteractComplete listener, Context context);
    void updateEvent(OnInteractComplete listener, Context context);

}
