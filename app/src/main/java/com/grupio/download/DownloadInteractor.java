package com.grupio.download;

import android.content.Context;

import com.grupio.BuildConfig;
import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.apis.APICallBackWithResponse;
import com.grupio.apis.ResourceListAPI;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by JSN on 28/11/16.
 */

public class DownloadInteractor implements DownloadContract.Interactor {

    @Override
    public void fetchResourceList(Context mContext, DownloadContract.OnInteration mListener) {
        File dir = Utility.getBaseFolder(mContext, BuildConfig.BASE_FOLDER);

        String[] directories = null;
        if (dir.canRead()) {
            directories = dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File file, String filename) {
                    File fileTemp = new File(dir, filename);

                    return fileTemp.isDirectory() && fileTemp.getName().equals(mContext.getString(R.string.session_resources)) || fileTemp.getName().equals(mContext.getString(R.string.attendee_resources))
                            || fileTemp.getName().equals(mContext.getString(R.string.sponsor_resources)) || fileTemp.getName().equals(mContext.getString(R.string.speaker_resources))
                            || fileTemp.getName().equals(mContext.getString(R.string.exhibitor_resources)) || fileTemp.getName().equals(mContext.getString(R.string.logistics_resources));
                }
            });
        }


//        if (directories != null) {
//
//            for (int i = 0; i < directories.length; i++) {
//                String[] file = new File(dir, directories[i]).list();
//
//                if (file != null) {
//                    DownloadedResource mResource = null;
//                    for (int j = 0; j < file.length; j++) {
//                        mResource = new DownloadedResource();
//                        mResource.name = file[j];
//                        mResource.section = directories[i];
//                        mResource.url = new File(dir + File.separator + directories[i], file[j]).getAbsolutePath();
//                        Log.i(mResource.section, mResource.url);
//                        fileList.add(mResource);
//                    }
//                }
//            }
//        }

    }

    @Override
    public void fetchAllResourceFromServer(Context mContext, DownloadContract.OnInteration mListener) {

        new ResourceListAPI(mContext, new APICallBackWithResponse() {
            @Override
            public void onSuccess(String response) {
            }

            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String msg) {
            }
        }).doCall();
    }

    @Override
    public void downloadAllResources(Context mContext, DownloadContract.OnInteration mListener) {

    }
}
