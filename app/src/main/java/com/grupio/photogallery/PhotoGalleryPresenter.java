package com.grupio.photogallery;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */

public class PhotoGalleryPresenter implements IPhotoGalleryContract.IPresenter, IPhotoGalleryContract.OnInteraction {

    private IPhotoGalleryContract.IView mListener;
    private PhotoGalleryInteractor mInteractor;

    public PhotoGalleryPresenter(IPhotoGalleryContract.IView mListener) {
        this.mListener = mListener;
        mInteractor = new PhotoGalleryInteractor();
    }

    @Override
    public void fetchPhotosList(Context mContext, boolean fromPreference) {
        mListener.showProgress("Loading Images...");
        mInteractor.fetchPhotosList(mContext, fromPreference, this);
    }

    @Override
    public void prepareList(Context mcontext) {
        mInteractor.prepareList(mcontext);
    }

    @Override
    public void uploadPhoto(Context mContext, String imagePath, String caption) {
        mListener.showProgress("Loading...");
        mInteractor.uploadPhoto(mContext, imagePath, caption, this);
    }

    @Override
    public void downloadImage(Context mContext, PhotoGalleryData mData, NotificationManager notificationManager, NotificationCompat.Builder mBuilder) {
        mInteractor.downloadImage(mContext, mData, notificationManager, mBuilder, this);
    }


    @Override
    public void onListFetch(List<PhotoGalleryData> mList) {
        mListener.hideProgress();
        mListener.showList(mList);
    }

    @Override
    public void onPhotoFetch(PhotoGalleryData mData) {
        mListener.showPhoto(mData);
    }

    @Override
    public void onPhotoUpload(PhotoGalleryData mData, Context mContext) {

        mListener.hideProgress();
        if (mInteractor.isModeratorOn(mContext)) {
            mListener.showModeratorDialog();
        } else {
            mListener.showSuccessDialog();
            mListener.showPhoto(mData);
        }

    }

    @Override
    public void onPhotoDownload() {

    }

    @Override
    public void onFailure(String msg) {
        mListener.hideProgress();
        mListener.onFailure(msg);
    }


}
