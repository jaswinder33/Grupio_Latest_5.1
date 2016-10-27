package com.grupio.gridhome;

import android.content.Context;

import com.grupio.data.MenuData;

import java.util.List;

/**
 * Created by JSN on 22/9/16.
 */
public interface GridInteractorImp {

    void fetchMenuList(OnInteractComplete listener, Context context);

    void fetchMessageCount(OnInteractComplete listener, Context context);

    void fetchAlertCount(OnInteractComplete listener, Context context);

    void fetchCalendarCount(OnInteractComplete listener, Context context);

    void updateMeeting(OnInteractComplete listener, Context context);

    void updateEvent(OnInteractComplete listener, Context context);
    interface OnInteractComplete {

        void onMenuList(List<MenuData> menuList);

        void onMessageCount(String msgCount, int position);

        void onAlertCount(String alertCount, int position);

        void onCalendarCount(String calCount, int position);

        void onChatCount(String count, int position);
        void onUpdateMeeting();
        void onUpdateEvent();

    }

}
