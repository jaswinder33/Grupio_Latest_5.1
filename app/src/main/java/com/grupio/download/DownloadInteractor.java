package com.grupio.download;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.grupio.BuildConfig;
import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.ResourceListAPI;
import com.grupio.data.DownloadedResource;
import com.grupio.helper.DownloadHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by JSN on 28/11/16.
 */

public class DownloadInteractor implements DownloadContract.Interactor {

    @Override
    public void fetchResourceList(Context mContext, DownloadContract.OnInteration mListener) {
        ArrayList<DownloadedResource> fileList = new ArrayList<>();
        File dir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = new File(Environment.getExternalStorageDirectory(), BuildConfig.BASE_FOLDER);
        } else {
            dir = mContext.getFilesDir();
        }

        if (!dir.exists())
            dir.mkdirs();

        String[] directories = null;
        if (dir.canRead()) {
            directories = dir.list((file, filename) -> {
                File fileTemp = new File(dir, filename);

                return fileTemp.isDirectory() && fileTemp.getName().equals(mContext.getString(R.string.session_resources)) || fileTemp.getName().equals(mContext.getString(R.string.attendee_resources))
                        || fileTemp.getName().equals(mContext.getString(R.string.sponsor_resources)) || fileTemp.getName().equals(mContext.getString(R.string.speaker_resources))
                        || fileTemp.getName().equals(mContext.getString(R.string.exhibitor_resources)) || fileTemp.getName().equals(mContext.getString(R.string.logistics_resources));
            });
        }
        if (directories != null) {
            for (int i = 0; i < directories.length; i++) {
                String[] file = new File(dir, directories[i]).list();
                if (file != null) {
                    DownloadedResource mResource = null;
                    for (int j = 0; j < file.length; j++) {
                        mResource = new DownloadedResource();
                        mResource.name = file[j];
                        mResource.section = directories[i];
                        mResource.url = new File(dir + File.separator + directories[i], file[j]).getAbsolutePath();
                        mResource.type = android.webkit.MimeTypeMap.getFileExtensionFromUrl(mResource.url);
                        fileList.add(mResource);
                    }
                }
            }
        }
        mListener.onListFetch(fileList);
    }

    @Override
    public void fetchAllResourceFromServer(Context mContext, DownloadContract.OnInteration mListener) {

        new ResourceListAPI(mContext, new APICallBackWithResponse() {
            @Override
            public void onSuccess(String response) {
                DownloadHelper downloadHelper = new DownloadHelper();
                mListener.onListFetch(downloadHelper.parseData(response, mContext));
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

    @Override
    public void downloadAllResources(Context mContext, List<DownloadedResource> mData, DownloadContract.OnInteration mListener) {

        List<DownloadedResource> mAttendeeRes = new ArrayList<>();
        List<DownloadedResource> mSpeakerRes = new ArrayList<>();
        List<DownloadedResource> mSponsorRes = new ArrayList<>();
        List<DownloadedResource> mLogisticRes = new ArrayList<>();
        List<DownloadedResource> mExhibitorRes = new ArrayList<>();
        List<DownloadedResource> mSessionRes = new ArrayList<>();

        for (int i = 0; i < mData.size(); i++) {
            String folderName = mData.get(i).section;
            switch (folderName) {

                case "Attendee Resources":
                    mAttendeeRes.add(mData.get(i));
                    break;
                case "Speakers Resources":
                    mSpeakerRes.add(mData.get(i));
                    break;
                case "Sponsors Resources":
                    mSponsorRes.add(mData.get(i));
                    break;
                case "Logistics Resources":
                    mLogisticRes.add(mData.get(i));
                    break;
                case "Exhibitor Resources":
                    mExhibitorRes.add(mData.get(i));
                    break;
                case "Session Resources":
                    mSessionRes.add(mData.get(i));
                    break;
            }
        }

        Map<String, List<DownloadedResource>> downloadQue = new HashMap<>();
        downloadQue.put(mContext.getString(R.string.attendee_resources), mAttendeeRes);
        downloadQue.put(mContext.getString(R.string.speaker_resources), mSpeakerRes);
        downloadQue.put(mContext.getString(R.string.sponsor_resources), mSponsorRes);
        downloadQue.put(mContext.getString(R.string.logistics_resources), mLogisticRes);
        downloadQue.put(mContext.getString(R.string.exhibitor_resources), mExhibitorRes);
        downloadQue.put(mContext.getString(R.string.session_resources), mSessionRes);


        new Thread(new Runnable() {
            @Override
            public void run() {

                List<DownloadedResource> templist;

                Iterator mIterator = downloadQue.entrySet().iterator();

                while (mIterator.hasNext()) {
                    Map.Entry<String, List<DownloadedResource>> mobj = (Map.Entry<String, List<DownloadedResource>>) mIterator.next();

                    int[] progress = new int[4];
                    String[] names = new String[2];

                    templist = new ArrayList<>();
                    templist.addAll(mobj.getValue());

                    for (int i = 0; i < templist.size(); i++) {

                        DownloadedResource mResource = templist.get(i);

                        names[0] = mobj.getKey(); //section Name
                        names[1] = mResource.name; //file name

                        progress[0] = templist.size(); //sections count
                        progress[1] = i; //current section index

                        progress[2] = 0;   //total size of file
                        progress[3] = 0;   // current size of file while downloading

                        mListener.onDownload(progress, names);

                        File fileDir = Utility.getBaseFolder(mContext, mResource.section);

                        Uri mUri = Uri.parse(mResource.url);

                        File mFile = new File(fileDir, mUri.getLastPathSegment());

                        URL url = null;
                        try {
                            url = new URL(mResource.url);
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

                        progress[2] = c.getContentLength();

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
                                progress[3] = total;
                                mListener.onDownload(progress, names);
                            }
                            fos.close();
                            is.close();
                            fos.flush();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                mListener.onDownloadComplete();

            }
        }).start();

    }
}
