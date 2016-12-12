package com.grupio.calendar;

import android.content.Context;

import com.grupio.base.IBaseInteractor;
import com.grupio.base.IBaseOnInteraction;
import com.grupio.base.IBasePresenter;
import com.grupio.base.IBaseView;

/**
 * Created by JSN on 12/12/16.
 */

public interface CalendarListContract {

    interface IView extends IBaseView {
    }

    interface IPresenter extends IBasePresenter {
        void fetchList(Context context);
    }

    interface IInteractor extends IBaseInteractor {
        void fetchList(Context context, IOnInteraction listener);
    }

    interface IOnInteraction extends IBaseOnInteraction {
    }
}
