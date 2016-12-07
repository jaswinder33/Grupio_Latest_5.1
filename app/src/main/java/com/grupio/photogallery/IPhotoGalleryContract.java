package com.grupio.photogallery;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.grupio.base.BaseInteractor;
import com.grupio.base.BaseOnInteraction;
import com.grupio.base.BasePresenter;
import com.grupio.base.BaseView;

import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */

public interface IPhotoGalleryContract {

    interface IView extends BaseView {
        void showList(List<PhotoGalleryData> mList);

        void showPhoto(PhotoGalleryData mData);

        void showModeratorDialog();

        void showSuccessDialog();

        void showDownloadNotification(PhotoGalleryData mData);

    }

    interface IPresenter extends BasePresenter {
        void fetchPhotosList(Context mContext, boolean fromPreference);

        void prepareList(Context mcontext);

        void uploadPhoto(Context mContext, String imagePath, String caption);

        void downloadImage(Context mContext, PhotoGalleryData mData, NotificationManager notificationManager, NotificationCompat.Builder mBuilder);
    }

    interface Interactor extends BaseInteractor {
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
