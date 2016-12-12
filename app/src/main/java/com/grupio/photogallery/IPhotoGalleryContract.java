package com.grupio.photogallery;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.grupio.base.BaseOnInteraction;
import com.grupio.base.IBaseInteractor;
import com.grupio.base.IBasePresenter;
import com.grupio.base.IBaseView;

import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */

public interface IPhotoGalleryContract {

    interface IViewI extends IBaseView {
        void showList(List<PhotoGalleryData> mList);

        void showPhoto(PhotoGalleryData mData);

        void showModeratorDialog();

        void showSuccessDialog();

        void showDownloadNotification(PhotoGalleryData mData);

    }

    interface IPresenterI extends IBasePresenter {
        void fetchPhotosList(Context mContext, boolean fromPreference);

        void prepareList(Context mcontext);

        void uploadPhoto(Context mContext, String imagePath, String caption);

        void downloadImage(Context mContext, PhotoGalleryData mData, NotificationManager notificationManager, NotificationCompat.Builder mBuilder);
    }

    interface InteractorI extends IBaseInteractor {
        void fetchPhotosList(Context mContext, boolean fromPreference, OnInteraction mListener);

        void prepareList(Context mcontext);

        void uploadPhoto(Context mContext, String imagePath, String caption, OnInteraction mListener);

        void downloadImage(Context mContext, PhotoGalleryData mData, NotificationManager notificationManager, NotificationCompat.Builder mBuilder, OnInteraction mListener);
    }

    interface OnInteraction extends BaseOnInteraction {
        void onListFetch(List<PhotoGalleryData> mList);

        void onPhotoFetch(PhotoGalleryData mData);

        void onPhotoUpload(PhotoGalleryData mData, Context mContext);

        void onPhotoDownload();
    }

}
