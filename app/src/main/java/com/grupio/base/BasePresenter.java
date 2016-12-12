package com.grupio.base;

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
        view.onFailure(msg);
    }
}
