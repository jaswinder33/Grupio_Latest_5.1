package com.grupio.gridhome;

import android.content.Context;

import com.grupio.data.MenuData;

import java.util.List;

/**
 * Created by JSN on 21/9/16.
 */
public class GridHomePresenter implements GridHomePresenterImp, GridInteractorImp.OnInteractComplete {

    GridHomeInteractor interactor;
    GridView listener;
    Context mContext;

    public GridHomePresenter(GridView listener, Context mContext) {
        this.listener = listener;
        this.mContext = mContext;
        interactor = new GridHomeInteractor();

        fetchMenuList();
    }

    @Override
    public void fetchMenuList() {
        interactor.fetchMenuList(this, mContext);
    }

    @Override
    public void fetchCalendarCount() {
        interactor.fetchCalendarCount(this, mContext);
    }

    @Override
    public void fetchMsgCount() {
        interactor.fetchMessageCount(this, mContext);
    }

    @Override
    public void fetchAlertCount() {
        interactor.fetchAlertCount(this, mContext);
    }

    @Override
    public void onMenuList(List<MenuData> menuList) {
        if (listener != null) {
            listener.showGrid(menuList);
        }
    }

    @Override
    public void onMessageCount(String msgCount, int position) {
        if (listener != null) {
            listener.messageCount(msgCount, position);
        }
    }

    @Override
    public void onAlertCount(String alertCount, int position) {
        if (listener != null) {
            listener.alertCount(alertCount, position);
        }
    }

    @Override
    public void onCalendarCount(String calCount, int position) {
        if (listener != null) {
            listener.showCalendarCount(calCount, position);
        }
    }

    @Override
    public void onChatCount(String count, int position) {
        if (listener != null) {
            listener.chatCount(count, position);
        }
    }

    @Override
    public void onUpdateMeeting() {

    }

    @Override
    public void onUpdateEvent() {

    }


}
