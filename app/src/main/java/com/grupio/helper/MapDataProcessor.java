package com.grupio.helper;

import android.content.Context;

import com.grupio.dao.VersionDao;
import com.grupio.data.MapsData;
import com.grupio.data.VersionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapDataProcessor {

    public static ArrayList<MapsData> getSponsorListFromJSON(Context mContext, String response) {

        ArrayList<MapsData> mapsDataList = new ArrayList<MapsData>();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }


         /*
            Store version in db
             */
        try {
            String version = jsonObject.getString("version");
            VersionData vData = new VersionData();
            vData.name = VersionDao.MAP_VERSION;
            vData.oldVersion = version;
            VersionDao.getInstance(mContext).insertDataInOldColumn(vData);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (jsonObject != null) {
            JSONArray jArray = null;
            try {
                jArray = jsonObject.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null && jArray.length() > 0) {

                MapsData data = null;

                for (int i = 0; i < jArray.length(); i++) {

                    data = new MapsData();
                    JSONObject jObject = null;
                    try {
                        jObject = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (jObject != null) {
                        try {
                            data.setName(jObject.getString("name").trim());
                        } catch (Exception e) {
                        }
                        try {
                            data.setUrl(jObject.getString("url").trim());
                        } catch (Exception e) {
                        }
                        try {
                            data.setInteractive(jObject.getString("Interactive").trim());
                        } catch (Exception e) {
                        }
                        try {
                            data.setOrder(jObject.getString("order_no").trim());
                        } catch (Exception e) {
                        }

                        mapsDataList.add(data);
                    }

                }
            }
        }


        return mapsDataList;
    }
}
