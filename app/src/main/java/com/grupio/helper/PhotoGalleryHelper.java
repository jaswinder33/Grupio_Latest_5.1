package com.grupio.helper;

import android.content.Context;

import com.grupio.photogallery.PhotoGalleryData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryHelper {

    public List<PhotoGalleryData> processData(String response, Context mContext) {

        List<PhotoGalleryData> photoGalleryList = new ArrayList<>();

        JSONArray mJsonArray = null;
        try {
            mJsonArray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mJsonArray != null && mJsonArray.length() > 0) {

            PhotoGalleryData obj = null;

            for (int i = 0; i < mJsonArray.length(); i++) {


                obj = new PhotoGalleryData();
                try {
                    obj.setId(mJsonArray.getJSONObject(i).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    obj.setEvent_id(mJsonArray.getJSONObject(i).getString("event_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    obj.setAttendee_id(mJsonArray.getJSONObject(i).getString("attendee_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    obj.setImage_url(mJsonArray.getJSONObject(i).getString("img_url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    obj.setThumb_image(mJsonArray.getJSONObject(i).getString("thumbnail_img_url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    obj.setCaption(mJsonArray.getJSONObject(i).getString("img_caption"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                photoGalleryList.add(obj);

            }
        }

        return photoGalleryList;
    }


}
