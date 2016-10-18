package com.grupio.helper;

import com.grupio.data.MapsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapDataProcessor {

    public static ArrayList<MapsData> getSponsorListFromJSON(String str) {

        ArrayList<MapsData> mapsDataList = new ArrayList<MapsData>();

        JSONArray jArray = null;
        try {
            jArray = new JSONObject(str).getJSONArray("data");
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

        return mapsDataList;
    }
}
