package com.grupio.helper;

import android.content.Context;

import com.grupio.dao.VersionDao;
import com.grupio.data.AdsData;
import com.grupio.data.VersionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 12/12/16.
 */

public class AdsHelper {

    public List<AdsData> parseData(String response, Context context) {

        List<AdsData> mAdsList = new ArrayList<>();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObj != null) {

            try {
                VersionData vData = new VersionData();
                vData.name = VersionDao.ADS_VERSION;
                vData.oldVersion = jObj.getString("version");
                VersionDao.getInstance(context).insertDataInOldColumn(vData);
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

                AdsData mAdData;
                for (int i = 0; i < jArray.length(); i++) {
                    mAdData = new AdsData();

                    try {
                        mAdData.id = jArray.getJSONObject(i).getString("ad_id").trim();
                    } catch (Exception e) {
                    }

                    try {
                        mAdData.adHtml = jArray.getJSONObject(i).getString("ad_html").trim();
                    } catch (Exception e) {
                    }

                    try {
                        mAdData.imageUrl = jArray.getJSONObject(i).getString("image_url").trim();
                    } catch (Exception e) {
                    }

                    try {
                        mAdData.goToUrl = jArray.getJSONObject(i).getString("goto_url").trim();
                    } catch (Exception e) {
                    }

                    try {
                        mAdData.section = jArray.getJSONObject(i).getString("section").trim();
                    } catch (Exception e) {
                    }

                    mAdsList.add(mAdData);
                }
            }
        }

        return mAdsList;
    }
}
