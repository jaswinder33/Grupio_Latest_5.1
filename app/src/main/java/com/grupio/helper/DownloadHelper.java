package com.grupio.helper;

import android.content.Context;
import android.webkit.MimeTypeMap;

import com.grupio.R;
import com.grupio.data.DownloadedResource;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 29/11/16.
 */

public class DownloadHelper {

    public List<DownloadedResource> parseData(String data, Context mcontext) {

        List<DownloadedResource> mList = new ArrayList<>();

        JSONArray mJsonArr = null;
        try {
            mJsonArr = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mJsonArr != null && mJsonArr.length() > 0) {

            DownloadedResource mResource;

            for (int i = 0; i < mJsonArr.length(); i++) {
                mResource = new DownloadedResource();

                try {
                    mResource.id = mJsonArr.getJSONObject(i).getString("session_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    mResource.name = mJsonArr.getJSONObject(i).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    mResource.url = mJsonArr.getJSONObject(i).getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    String resourceType = mJsonArr.getJSONObject(i).getString("type");

                    switch (resourceType) {
                        case "speaker":
                            mResource.section = mcontext.getString(R.string.speaker_resources);
                            break;
                        case "attendee":
                            mResource.section = mcontext.getString(R.string.attendee_resources);
                            break;
                        case "sponsor":
                            mResource.section = mcontext.getString(R.string.sponsor_resources);
                            break;
                        case "logistics":
                            mResource.section = mcontext.getString(R.string.logistics_resources);
                            break;
                        case "session":
                            mResource.section = mcontext.getString(R.string.session_resources);
                            break;
                        case "exhibitor":
                            mResource.section = mcontext.getString(R.string.exhibitor_resources);
                            break;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    mResource.typeName = mJsonArr.getJSONObject(i).getString("typeName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mResource.type = MimeTypeMap.getFileExtensionFromUrl(mResource.url);

                mList.add(mResource);

            }
        }

        return mList;

    }

}
