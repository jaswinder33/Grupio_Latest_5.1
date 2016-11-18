package com.grupio.services;

import android.content.Context;

import com.grupio.message.apis.APICallBack;
import com.grupio.session.Preferences;
import com.grupio.social.LinkedinDialog;

/**
 * Created by JSN on 15/11/16.
 */

public class LinkedinShare implements ServiceContract<String> {

    @Override
    public void sendMessage(String str, Context mContext, APICallBack mListener) {

        LinkedinDialog mLinkedinDialog = new LinkedinDialog(mContext, str);

        mLinkedinDialog.setVerifierListener((isPosted, message) -> {
            if (isPosted) {
                mListener.onSuccess();
            } else {
                mListener.onFailure(message);
            }
            mLinkedinDialog.dismiss();
        });
        if (Preferences.getInstances(mContext).getLinedinToken() != null) {
            mLinkedinDialog.new PostRequestAsyncTask().execute("");
        } else {
            mLinkedinDialog.show();
        }

    }
}
