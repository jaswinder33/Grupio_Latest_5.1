package com.grupio.photogallery;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.grupio.base.BasePresenter;

import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */

public class PhotoGalleryPresenterImp extends BasePresenter<IPhotoGalleryContract.IViewI, IPhotoGalleryContract.InteractorI> implements IPhotoGalleryContract.IPresenterI, IPhotoGalleryContract.OnInteraction {

    public PhotoGalleryPresenterImp(IPhotoGalleryContract.IViewI view) {
        super(view);
        setInteractor(new PhotoGalleryInteractorI());
    }

    @Override
    public void fetchPhotosList(Context mContext, boolean fromPreference) {
        getView().showProgress("Loading Images...");
        getInteractor().fetchPhotosList(mContext, fromPreference, this);
    }

    @Override
    public void prepareList(Context mcontext) {
        getInteractor().prepareList(mcontext);
    }

    @Override
    public void uploadPhoto(Context mContext, String imagePath, String caption) {
        getView().showProgress("Loading...");
        getInteractor().uploadPhoto(mContext, imagePath, caption, this);
    }

    @Override
    public void downloadImage(Context mContext, PhotoGalleryData mData, NotificationManager notificationManager, NotificationCompat.Builder mBuilder) {
        getInteractor().downloadImage(mContext, mData, notificationManager, mBuilder, this);
    }


    @Override
    public void onListFetch(List<PhotoGalleryData> mList) {
        getView().hideProgress();
        getView().showList(mList);
    }

    @Override
    public void onPhotoFetch(PhotoGalleryData mData) {
        getView().showPhoto(mData);
    }

    @Override
    public void onPhotoUpload(PhotoGalleryData mData, Context mContext) {
        getView().hideProgress();

        if (getInteractor().isModeratorOn(mContext)) {
            getView().showModeratorDialog();
        } else {
            getView().showSuccessDialog();
            getView().showPhoto(mData);
        }
    }

    @Override
    public void onPhotoDownload() {
    }

    @Override
    public void onFailure(String msg) {
        getView().hideProgress();
        getView().onFailure(msg);
    }

}
