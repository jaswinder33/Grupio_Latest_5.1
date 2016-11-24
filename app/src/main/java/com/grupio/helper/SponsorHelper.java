package com.grupio.helper;

import android.content.Context;
import android.graphics.Bitmap;

import com.grupio.R;
import com.grupio.dao.VersionDao;
import com.grupio.data.LogisticsData;
import com.grupio.data.SponsorData;
import com.grupio.data.VersionData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 21/11/16.
 */

public class SponsorHelper {
    public static DisplayImageOptions sponsorDisplayOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.no_image_l)
                .showImageForEmptyUri(R.drawable.no_image_l)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.no_image_l).cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888).considerExifParams(true)
                .build();
    }

    public List<SponsorData> parseData(String response, Context mContext) {

        List<SponsorData> mSponsorList = new ArrayList<>();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObj != null) {

            try {
                String version = jObj.getString("version");

                VersionData vData = new VersionData();
                vData.oldVersion = version;
                vData.name = VersionDao.SPONSORS_VERSION;
                vData.newVersion = "";
                VersionDao.getInstance(mContext).insertDataInOldColumn(vData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jArray = null;
            try {
                jArray = jObj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null && jArray.length() > 0) {

                SponsorData mData;
                for (int i = 0; i < jArray.length(); i++) {
                    mData = new SponsorData();
                    try {
                        mData.sponsorId = jArray.getJSONObject(i).getString("sponsor_id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.name = jArray.getJSONObject(i).getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.type = jArray.getJSONObject(i).getString("type");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.url = jArray.getJSONObject(i).getString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.largeUrl = jArray.getJSONObject(i).getString("large_url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.webUrl = jArray.getJSONObject(i).getString("weburl");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.sponsorSessions = jArray.getJSONObject(i).getString("sponsor_sessions");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.phone = jArray.getJSONObject(i).getString("contact_phone");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.email = jArray.getJSONObject(i).getString("contact_email");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.booth = jArray.getJSONObject(i).getString("booth");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.description = jArray.getJSONObject(i).getString("description");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.summary = jArray.getJSONObject(i).getString("summary");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.sponsorLink = jArray.getJSONObject(i).getJSONArray("sponsorlinks").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mSponsorList.add(mData);
                }
            }
        }
        return mSponsorList;

    }

    public List<LogisticsData> parseResourceList(String response) {

        List<LogisticsData> mLogisticsList = new ArrayList<>();

        JSONArray jArray = null;
        try {
            jArray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (jArray != null && jArray.length() > 0) {
            LogisticsData mData;

            for (int i = 0; i < jArray.length(); i++) {
                mData = new LogisticsData();

                try {
                    mData.setUrl(jArray.getJSONObject(i).getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mData.setType(jArray.getJSONObject(i).getString("type"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    mData.setName(jArray.getJSONObject(i).getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mData.setLogistics_id(String.valueOf(i));

                mLogisticsList.add(mData);

            }
        }

        return mLogisticsList;
    }
}
