package com.grupio.logistics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.activities.WebViewActivity;
import com.grupio.animation.SlideOut;
import com.grupio.apis.DownloadFile;
import com.grupio.data.AttendeesData;
import com.grupio.data.DocInter;
import com.grupio.data.ExhibitorData;
import com.grupio.data.LogisticsData;
import com.grupio.data.MapsData;
import com.grupio.data.ScheduleData;
import com.grupio.data.SpeakerData;
import com.grupio.data.mapList;
import com.grupio.message.apis.APICallBack;

import java.io.File;

/**
 * Created by JSN on 3/8/16.
 */
public class DocumentController<K, V extends DocInter> {


    /**
     * Type K belongs to following categories:
     *
     * @attr attendee
     * @attr exhibitors
     * @attr sponsors
     * @attr speakers
     * @attr sessions
     */
    private K typeK;

    /**
     * Type V  belongs to following
     *
     * @attr LogisticsData
     * @attr mapList
     */
    private V typeV;

    private mapList mapListObj;
    private LogisticsData mLogisticData;

    // name of file in name params of object
    private String name;
    // url of file
    private String url;
    // id param of object
    private String id;
    //Extension of file
    private String type;

    /**
     * File belong to which different folders
     *
     * @attr attendee
     * @attr exhibitors
     * @attr Logistics
     * @attr sponsors
     * @attr speakers
     * @attr sessions
     */
    private String folderName;

    private Context mContext;

    //File name stored on device
    private String fName;

    /**
     * Constructor
     *
     * @param typeK    type of K
     * @param typeV    type of V
     * @param mContext
     */
    public DocumentController(K typeK, V typeV, Context mContext) {
        this.typeK = typeK;
        this.typeV = typeV;
        this.mContext = mContext;

    }

    /**
     * Handle download event
     *
     * @param objV object of logistics/maplist. actuall data required to process on
     */
    public void downloadResource(V objV) {

        createVariables(objV);
        if (checkIfFileExists()) {
            AlertDialog mdialog = new AlertDialog.Builder(mContext).setMessage("This file already exists. Do you want to redownload this file?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadFile(false);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();

            mdialog.show();
        } else {
            downloadFile(false);
        }
    }

    /**
     * View doc if exists on local device otherwise download file
     *
     * @param objV actuall data required to process on
     */
    public void viewDoc(V objV) {
        createVariables(objV);

        if (checkIfFileExists()) {
            if (Utility.isValidType(type)) {
                File mFile = new File(Utility.getBaseFolder(mContext, folderName), fName);
                Utility.openDoc(mContext, mFile);
            }
        } else {
            downloadFile(true);
        }
    }

    /**
     * Open files in appropriate way.
     *
     * @param objV
     */
    public void handleDocument(V objV) {

        createVariables(objV);

        Uri uri = Uri.parse(url);
        String videoId = uri.getQueryParameter("v");

        if (videoId != null && !videoId.equals("")) {
            openYoutubeVideo(videoId);
        } else if (type.equals("3gp") || type.equals("mp4")) {
            open3gpOrmp4();
        } else {
            openWebView();
        }
    }


    /**
     * Assigns values to global variables from object
     *
     * @param objV
     */
    public void createVariables(V objV) {
        if (typeV instanceof mapList) {
            mapListObj = (mapList) objV;

            name = mapListObj.getMapName();
            url = mapListObj.getMapUrl();

        } else if (typeV instanceof LogisticsData) {
            mLogisticData = (LogisticsData) objV;

            name = mLogisticData.getName();
            url = mLogisticData.getUrl();
            id = mLogisticData.getLogistics_id();

        } else if (typeV instanceof MapsData) {
            MapsData mData = (MapsData) objV;

            name = mData.getName();
            url = mData.getUrl();
            id = mData.getMapId();
        }

        try {
            Uri uri = Uri.parse(url);
            fName = uri.getLastPathSegment();
            if (fName != null) {
                type = android.webkit.MimeTypeMap.getFileExtensionFromUrl(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        type = (type != null) ? type : "";

        // will assign folder name if type is any doc/ppt/pdf file
        if (Utility.isValidType(type)) {
            guessFolderName();
        }

    }


    /**
     * Open video file
     */
    public void open3gpOrmp4() {
//        Intent VideoActivityIntent = new Intent(mContext, VideoActivity.class);
//        VideoActivityIntent.putExtra("videoUrl", url);
//        VideoActivityIntent.putExtra("videoName", name);
//        mContext.startActivity(VideoActivityIntent);
//        GridHome.ut_obj.callShowAnimationInToOut(mContext);
    }

    /**
     * Open youtube video
     *
     * @param videoId
     */
    public void openYoutubeVideo(String videoId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        intent.putExtra("VIDEO_ID", videoId);
        mContext.startActivity(intent);
        SlideOut.getInstance().startAnimation((Activity) mContext);
    }

    /**
     * Open link in webview
     */
    public void openWebView() {
        Intent mapIntent = new Intent(mContext, WebViewActivity.class);
        mapIntent.putExtra("url", url);
        mContext.startActivity(mapIntent);
        SlideOut.getInstance().startAnimation((Activity) mContext);
    }

    /**
     * Assign Folder name according to type of typeK variable.
     */
    public void guessFolderName() {
        if (typeK instanceof AttendeesData) {
            folderName = mContext.getString(R.string.attendee_resources);
        } else if (typeK instanceof SpeakerData) {
            folderName = mContext.getString(R.string.speaker_resources);
        } else if (typeK instanceof ExhibitorData) {
            folderName = mContext.getString(R.string.exhibitor_resources);
        } else if (typeK instanceof ScheduleData) {
            folderName = mContext.getString(R.string.session_resources);
        } else {
            folderName = mContext.getString(R.string.logistics_resources);
        }
    }

    /**
     * Check if file exists on local device
     *
     * @return
     */
    public boolean checkIfFileExists() {
        return Utility.isThisfileExists(mContext, folderName, fName);
    }

    /**
     * Download file from server
     *
     * @param openFile
     */
    public void downloadFile(boolean openFile) {

        ((BaseActivity) mContext).showProgressDialog("Loading...");

        DownloadFile downloadFile = new DownloadFile(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                ((BaseActivity) mContext).hideProgressDialog();
                if (openFile) {
                    File mFile = new File(Utility.getBaseFolder(mContext, folderName), fName);
                    Utility.openDoc(mContext, mFile);
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.file_downloaded), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String msg) {
                ((BaseActivity) mContext).hideProgressDialog();
                Toast.makeText(mContext, mContext.getString(R.string.file_download_failed), Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext, LocalisationDataProcessor.PROBLEM_LOADING_REQUEST, Toast.LENGTH_SHORT).show();
            }
        });
        downloadFile.doCall(url, folderName);
    }
}
