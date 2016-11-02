package com.grupio.helper;

import com.grupio.data.AlertData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 2/11/16.
 */

public class AlertHelper {

    public List<AlertData> parseList(String response) {

        List<AlertData> mAlertList = new ArrayList<>();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObj != null) {

            JSONArray jArray = null;
            try {
                jArray = jObj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null && jArray.length() > 0) {
                AlertData mData;
                for (int i = 0; i < jArray.length(); i++) {
                    mData = new AlertData();

                    try {
                        mData.setAlertId(jArray.getJSONObject(i).getString("alert_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setDate(jArray.getJSONObject(i).getString("datetime"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        mData.setNotificationText(jArray.getJSONObject(i).getString("notification_text"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mData.setRead(jArray.getJSONObject(i).getString("is_read").equals("y"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mAlertList.add(mData);
                }
            }
        }

        return mAlertList;

    }

}
