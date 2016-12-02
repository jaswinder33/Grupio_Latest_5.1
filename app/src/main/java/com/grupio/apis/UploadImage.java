package com.grupio.apis;

import android.content.Context;

import com.grupio.BuildConfig;
import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.ImageUpload;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 30/11/16.
 */

public class UploadImage extends BaseAsyncTask<String, String> {
    public UploadImage(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.upload_image_api);
    }

    @Override
    public String handleBackground(String... params) {

//        Bitmap bm = BitmapFactory.decodeFile(params[0]);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("Content-Type", "multipart/form-data");
        customHeaders.put("Connection", "Keep-Alive");
        customHeaders.put("Cookie", "attendee_id=" + Preferences.getInstances(mContext).getAttendeeId() + ";" + "event_id=" + ConstantData.EVENT_ID + ";" + "organization_id=" + BuildConfig.ORG_ID);

        APIRequest request = new ImageUpload();
        ((ImageUpload) request).setHeader(customHeaders);
        String response = request.makeRequest(url, params[0]);

        return response;
    }
}
