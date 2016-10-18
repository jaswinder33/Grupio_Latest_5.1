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
    public void onMessageCount(String msgCount) {
        if (listener != null) {
            listener.messageCount(msgCount);
        }
    }

    @Override
    public void onAlertCount(String alertCount) {
        if (listener != null) {
            listener.alertCount(alertCount);
        }
    }

    @Override
    public void onCalendarCount(String calCount) {
        if (listener != null) {
            listener.showCalendarCount(calCount);
        }
    }

    @Override
    public void onUpdateMeeting() {

    }

    @Override
    public void onUpdateEvent() {

    }


}
