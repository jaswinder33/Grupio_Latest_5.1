package com.grupio.photogallery;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.PhotoGalleryAPI;
import com.grupio.apis.PhotoGalleryImageUpload;
import com.grupio.dao.EventDAO;
import com.grupio.db.EventTable;
import com.grupio.helper.PhotoGalleryHelper;
import com.grupio.session.Preferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 5/12/16.
 */

public class PhotoGalleryInteractor implements IPhotoGalleryContract.Interactor {

    private List<PhotoGalleryData> mPhotoGalleryList = new ArrayList<>();

    @Override
    public void fetchPhotosList(Context mContext, boolean fromPreference, IPhotoGalleryContract.OnInteraction mListener) {

        if (fromPreference) {
            mListener.onListFetch(mPhotoGalleryList);
        } else {
            new PhotoGalleryAPI(mContext, new APICallBackWithResponse() {
                @Override
                public void onSuccess(String response) {
                    prepareList(mContext);
                    mListener.onListFetch(mPhotoGalleryList);
                }

                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure(String msg) {
                    mListener.onFailure(msg);
                }
            }).doCall();
        }
    }

//    @Override
//    public void fetchPhoto(Context mcontext, String id, IPhotoGalleryContract.OnInteraction mListener) {
//        if (mPhotoGalleryList.isEmpty()) {
//            prepareList(mcontext);
//        }
//
//        for (int i = 0; i < mPhotoGalleryList.size(); i++) {
//            if (mPhotoGalleryList.get(i).getId().equals(id)) {
//                mListener.onPhotoFetch(mPhotoGalleryList.get(i));
//                break;
//            }
//        }
//    }

    @Override
    public void prepareList(Context mcontext) {
        String response = Preferences.getInstances(mcontext).getPhotoGalleryData();

        PhotoGalleryHelper pHeler = new PhotoGalleryHelper();
        mPhotoGalleryList.addAll(pHeler.processData(response, mcontext));
    }

    @Override
    public void uploadPhoto(Context mContext, String imagePath, String caption, IPhotoGalleryContract.OnInteraction mListener) {

        new PhotoGalleryImageUpload(mContext, new APICallBackWithResponse() {
            @Override
            public void onSuccess(String response) {
                PhotoGalleryHelper pHelper = new PhotoGalleryHelper();
                mListener.onPhotoUpload(pHelper.processData(response, mContext).get(0), mContext);
            }

            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String msg) {
                mListener.onFailure(msg);
            }
        }).doCall(imagePath, caption);
    }

    @Override
    public void downloadImage(Context mContext, PhotoGalleryData mData, NotificationManager notificationManager, NotificationCompat.Builder mBuilder, IPhotoGalleryContract.OnInteraction mListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imageUrl = mContext.getString(R.string.image_base_url) + mData.getImage_url();
                Uri imageUri = Uri.parse(imageUrl);

                mBuilder.setContentTitle(imageUri.getLastPathSegment())
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.icon);
                mBuilder.setProgress(100, 0, false);
                notificationManager.notify(Integer.parseInt(mData.getId()), mBuilder.build());

                URL url = null;
                try {
                    url = new URL(imageUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                HttpURLConnection c = null;
                try {
                    c = (HttpURLConnection) url.openConnection();
                    c.setRequestMethod("GET");
                    c.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int totalProgress = c.getContentLength();

                mBuilder.setProgress(totalProgress, 0, false);
                notificationManager.notify(Integer.parseInt(mData.getId()), mBuilder.build());

                File fileDir = Utility.getBaseFolder(mContext, mContext.getString(R.string.image));
                File mFile = new File(fileDir, imageUri.getLastPathSegment());

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mFile);
                    InputStream is = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len = 0;
                    int total = 0;

                    while ((len = is.read(buffer)) != -1) {
                        total += len;
                        fos.write(buffer, 0, len);
                        mBuilder.setProgress(totalProgress, total, false);
                        notificationManager.notify(Integer.parseInt(mData.getId()), mBuilder.build());
                    }
                    fos.close();
                    is.close();
                    fos.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri selectedUri = Uri.parse("file://" + mFile.getAbsolutePath());
                Intent pendingIntent = new Intent(Intent.ACTION_VIEW);
                pendingIntent.setDataAndType(selectedUri, "image/*");

                PackageManager packageManager = mContext.getPackageManager();
                List intentList = packageManager.queryIntentActivities(pendingIntent, PackageManager.MATCH_DEFAULT_ONLY);

                if (intentList.size() > 0) {
                    PendingIntent mPendingIntent = PendingIntent.getActivities(mContext, 0, new Intent[]{pendingIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(mPendingIntent);
                }

                mBuilder.setContentText("Download complete")
                        .setProgress(0, 0, false);

                notificationManager.notify(Integer.parseInt(mData.getId()), mBuilder.build());
            }
        }).start();
    }

    public boolean isModeratorOn(Context mContext) {
        return EventDAO.getInstance(mContext).getValue(EventTable.IS_MODERATORAVAILABLE).equals("y");
    }

}
