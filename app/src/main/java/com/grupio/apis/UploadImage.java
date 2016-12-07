package com.grupio.apis;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.ImageUpload;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 30/11/16.
 */

public class UploadImage extends BaseAsyncTask<String, String> {

    public static final String IMAGE_URL = "image";
    public static final String LARGE_URL = "large_image";


    public UploadImage(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.upload_image_api);
    }

    @Override
    public String handleBackground(String... params) {

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("Cookie", "attendee_id=" + Preferences.getInstances(mContext).getAttendeeId());

        APIRequest request = new ImageUpload();
        ((ImageUpload) request).setHeader(customHeaders);
        String response = request.makeRequest(url, params[0]);

        /*
        {"status_code":"0","description":"Image Uploaded!","image":"\/eventimages\/151\/attendee_73422936_15.jpg","large_image":"\/eventimages\/151\/attendee_large_73422936_15.jpg"}
         */

        if (!TextUtils.isEmpty(response)) {

            JSONObject jObj = null;
            try {
                jObj = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jObj != null) {
                boolean status = false;
                try {
                    status = jObj.getString("status_code").equals("0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (status) {
                    try {
                        String smallUrl = jObj.getString("image");
                        String largeUrl = jObj.getString("large_image");

                        updateUserProfile(smallUrl, largeUrl);

                        return largeUrl;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return "";
    }


    public void updateUserProfile(String imageUrl, String largeImage) {

        String userdata = Preferences.getInstances(mContext).getUserInfo();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userdata);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (userdata != null) {
            JSONArray jArray = null;
            try {
                jArray = jsonObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jArray != null && jArray.length() > 0) {

                JSONObject jObj = null;
                try {
                    jObj = jArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jObj != null) {


                    jObj.remove(IMAGE_URL);
                    try {
                        jObj.put(IMAGE_URL, imageUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jObj.remove(LARGE_URL);
                    try {
                        jObj.put(LARGE_URL, largeImage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jParentObj = new JSONObject();
                    JSONArray mJsonArray = new JSONArray();
                    mJsonArray.put(jObj);
                    try {
                        jParentObj.put("data", mJsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Preferences.getInstances(mContext).saveUserInfo(jParentObj.toString());

                }
            }
        }
    }

}
