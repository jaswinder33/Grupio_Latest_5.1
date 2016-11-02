package com.grupio.apis;

import android.content.Context;
import android.net.Uri;

import com.grupio.Utils.Utility;
import com.grupio.message.apis.APICallBack;

/**
 * Created by JSN on 17/10/16.
 */

public class DownloadFile extends BaseAsyncTask<String, Boolean> {

    public DownloadFile(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return null;
    }

    @Override
    public Boolean handleBackground(String... params) {

        url = params[0];
        String folder = params[1];
        String fileName = null;
        String fileType = null;

        Uri uri = Uri.parse(url);
        try {
            fileName = uri.getLastPathSegment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Utility.downloadFile(mContext, url, fileName, folder, "");

        return Utility.isThisfileExists(mContext, folder, fileName);
    }
}
