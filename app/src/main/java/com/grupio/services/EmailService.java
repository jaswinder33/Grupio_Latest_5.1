package com.grupio.services;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.grupio.data.EmailData;
import com.grupio.message.apis.APICallBack;

import java.util.List;

/**
 * Created by JSN on 17/10/16.
 */

public class EmailService implements ServiceContract<EmailData> {


    @Override
    public void sendMessage(EmailData emailData, Context mContext, APICallBack mListener) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/html");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailData.emailId});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailData.subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailData.text);

            ResolveInfo best = null;
            try {
                final PackageManager pm = mContext.getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);

                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                        best = info;
                        break;
                    }
                }
            } catch (Exception e) {
            }

            if (best != null) {
                emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                mContext.startActivity(emailIntent);
            } else {
                emailIntent.setType("message/rfc822");
                mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        } catch (Exception e) {
        }
    }
}
