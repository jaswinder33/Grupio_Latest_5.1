package com.grupio.base;

import android.content.Context;

import com.grupio.data.AdsData;

import java.util.List;

/**
 * Created by JSN on 12/12/16.
 */

public abstract class BasePresenter<V extends IBaseView, I extends IBaseInteractor> implements IBasePresenter, IBaseOnInteraction {
    private V view;
    private I interactor;

    public BasePresenter(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }

    protected I getInteractor() {
        return interactor;
    }

    protected void setInteractor(I interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onFailure(String msg) {
        view.hideProgress();
        view.onFailure(msg);
    }

    @Override
    public void onShowBanner(List<AdsData> adData) {
        getView().showBanner(adData);
    }

    @Override
    public void showBanner(String bannerName, Context context) {
        interactor.showBanner(bannerName, context, this);
    }

    @Override
    public void sendReport(String screenName, Context context) {
        interactor.sendReport(screenName, context, this);
    }
}
