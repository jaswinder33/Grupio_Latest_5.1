package com.grupio.apis;

import android.content.Context;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.ImageUpload;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 6/12/16.
 */

public class PhotoGalleryImageUpload extends BaseAsyncTask<String, String> {

    public PhotoGalleryImageUpload(Context mcontext, APICallBack mListener) {
        super(mcontext, mListener);
    }

    @Override
    public String endPoint() {
        return mContext.getString(R.string.photogallery_upload_image);
    }

    @Override
    public String handleBackground(String... params) {

        try {
            url += "?caption=" + URLEncoder.encode(params[1], "UTF-16");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("Cookie", "attendee_id=" + Preferences.getInstances(mContext).getAttendeeId());

        APIRequest request = new ImageUpload();
        ((ImageUpload) request).setHeader(customHeaders);
        String response = request.makeRequest(url, params[0]);

        //{"link_url":"eventimages\/151\/attendee_image_7342293614810182360797.jpg","link_thumbnail_url":"eventimages\/151\/attendee_thumbimage_7342293614810182360797.jpg","img_caption":"caption","status_code":"0"}

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
                addPhotoToPreference(response);
                return Preferences.getInstances(mContext).getPhotoGalleryData();
            }
        }

        return null;
    }

    public void addPhotoToPreference(String apiResponse) {

        String response = Preferences.getInstances(mContext).getPhotoGalleryData();

        JSONArray jAray = null;
        try {
            jAray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jAray != null && jAray.length() > 0) {

            JSONObject jDestinationObj = null;
            try {
                jDestinationObj = new JSONObject(apiResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jDestinationObj != null) {

                JSONObject jObj = new JSONObject();
                try {
                    jObj.put("id", jDestinationObj.get("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jObj.put("event_id", ConstantData.EVENT_ID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jObj.put("attendee_id", Preferences.getInstances(mContext).getAttendeeId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jObj.put("img_url", jDestinationObj.get("link_url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jObj.put("thumbnail_img_url", jDestinationObj.get("link_thumbnail_url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jObj.put("img_caption", jDestinationObj.get("img_caption"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jObj.put("is_verified", "y");
                    jObj.put("created", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    jAray.put(0, jObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                ((APICallBackWithResponse) mListener).onSuccess(jAray.toString());

                Preferences.getInstances(mContext).savePhotogalleryData(jAray.toString());
            }
        }
    }
}
